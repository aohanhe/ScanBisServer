package com.ao.scanElectricityBis.service;


import java.util.Date;


import javax.persistence.EntityManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanSeverExpressionMaps;
import com.ao.scanElectricityBis.entity.PlugInfoMongoEntity;
import com.ao.scanElectricityBis.entity.QBaseOperator;
import com.ao.scanElectricityBis.entity.QStationDevice;
import com.ao.scanElectricityBis.entity.QStationPlugInfo;
import com.ao.scanElectricityBis.entity.QStationStationInfo;
import com.ao.scanElectricityBis.entity.StationPlugInfo;
import com.ao.scanElectricityBis.repository.PlugInfoMongoRepository;
import com.ao.scanElectricityBis.repository.PlugInfoRepository;

import com.querydsl.core.Tuple;

import com.querydsl.jpa.impl.JPAQuery;



@Service
public class PlugInfoService extends BaseService<StationPlugInfo, PlugInfoRepository> {
	private Logger logger = LoggerFactory.getLogger(PlugInfoService.class);

	public static final int PlugStatus_Free = 0; // ����
	public static final int PlugStatus_Working = 1;// �����
	public static final int PlugStatus_WorkingAndLow = 2;// ���ڳ�磬��������С
	public static final int PlugStatus_StopByOverFlow = 3;// ���������ѶϿ����
	public static final int PlugStatus_StopWaitContiue = 4;// �������У�ͣ���磬�ȴ��������·�ָ���Ƿ�����ָ����
	public static final int PlugStatus_OtherFault = 5;// ��������
	public static final int PlugStatus_CommFault = 6;// ��Ӧ�˵�ַ��ŵĲ�ͷͨѶʧ��

	

	private ScanSeverExpressionMaps<StationPlugInfo> selectList;

	public PlugInfoService() {
		super(QStationPlugInfo.stationPlugInfo);
	}

	@Override
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		var plugInfo = QStationPlugInfo.stationPlugInfo;
		var device = QStationDevice.stationDevice;
		var station = QStationStationInfo.stationStationInfo;
		var operator = QBaseOperator.baseOperator;

		try {
			if (selectList == null) {
				selectList = new ScanSeverExpressionMaps<StationPlugInfo>(plugInfo, StationPlugInfo.class);
				selectList.putItem("deviceid", device.id);
				selectList.putItem("deviceCode", device.code);
				selectList.putItem("stationId", device.stationId);

				selectList.putItem("station", station.name);
				selectList.putItem("price", station.price);
				selectList.putItem("regioncode", station.regioncode);
				selectList.putItem("address", station.address);
				selectList.putItem("operatorId", station.operatorid);

				selectList.putItem("operator", operator.name);
			}

			var query = factory.select(selectList.getExpressionArray()).from(plugInfo).leftJoin(device)
					.on(plugInfo.deviceid.eq(device.id)).leftJoin(station).on(device.stationId.eq(station.id))
					.leftJoin(operator).on(station.operatorid.eq(operator.id));

			return selectList.addExtendsLeftJoin(query);

		} catch (Exception ex) {
			logger.error("������ѯ����ʧ��:" + ex.getMessage(), ex);
			throw new ScanElectricityException("������ѯ����ʧ��:" + ex.getMessage(), ex);
		}

	}

	/**
	 * ת��mongodb��idֵ
	 * 
	 * @param deviceId
	 * @param index
	 * @return
	 */
	private long getPlugInfoMongoId(int deviceId, int index) {
		return deviceId * 1000 + index;
	}

	@Override
	protected StationPlugInfo fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {

		try {
			var res = selectList.fectionDataInItem(tuple);

			var item = this.plugRep.findById(res.getDeviceid() * 1000 + res.getDeviceindex());
			if (item.isPresent()) {
				res.setCurStatus(item.get().getStatus());
				res.setLastUpTime(item.get().getLastUpTime());
				res.setWorking(item.get().isWorking());
			}

			return res;
		} catch (Exception ex) {
			logger.error("�����ѯʧ��:" + ex.getMessage(), ex);
			throw new RuntimeException("�����ѯʧ��:" + ex.getMessage(), ex);
		}
	}

	@Override
	protected StationPlugInfo onSave(StationPlugInfo item) throws ScanElectricityException {

		var res = super.onSave(item);

		updateDevicePlugNumber(item.getDeviceid());

		return res;
	}

	@Override
	protected StationPlugInfo onSaveNew(StationPlugInfo item) throws ScanElectricityException {

		var res = super.onSaveNew(item);
		updateDevicePlugNumber(item.getDeviceid());

		return res;
	}

	@Override
	protected void onDeleteItemById(int id) throws ScanElectricityException {
		var item = this.findItemById(id, StationPlugInfo.class).block();
		super.onDeleteItemById(id);
		updateDevicePlugNumber(item.getDeviceid());
	}

	/**
	 * �����豸�Ĳ�ͷ����
	 * 
	 * @param deviceId
	 */
	public void updateDevicePlugNumber(int deviceId) {
		try {
			var deivce = QStationDevice.stationDevice;
			var pluginfo = QStationPlugInfo.stationPlugInfo;

			int totalNumber = 0;
			int faultNumber = 0;

			var res = factory.select(pluginfo.id.count(), pluginfo.isfault).from(pluginfo)
					.where(pluginfo.deviceid.eq(deviceId)).groupBy(pluginfo.isfault).fetch();

			for (var item : res) {
				totalNumber += item.get(0, Integer.class);
				if (item.get(1, Boolean.class)) {
					faultNumber += item.get(0, Integer.class);
				}
			}

			factory.update(deivce).set(deivce.totalNumber, totalNumber).set(deivce.faultNumber, faultNumber).execute();

		} catch (Exception ex) {
			logger.error("�����豸��ͷ��ʧ��:" + ex.getMessage(), ex);
			throw new RuntimeException("�����豸��ͷ��ʧ��:" + ex.getMessage(), ex);
		}

	}

	@Autowired
	private PlugInfoMongoRepository plugRep;

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * ����ָ���豸��ָ������Ĳ�ͷ����״̬
	 * 
	 * @param deviceId
	 * @param index
	 * @param isWorking
	 */
	public void updatePlugStatus(int deviceId, int index, int status) {
		
		
		var crite= Criteria.where("_id")
				.is(getPlugInfoMongoId(deviceId, index));
		
		var update = Update.update("status", status).set("lastUpTime", new Date())
				.set("deviceId", deviceId);

		mongoTemplate.upsert(new Query(crite), update, PlugInfoMongoEntity.class);

	}

	/**
	 * ����ָ���豸��ָ������Ĳ�ͷ�Ƿ��ڹ�����
	 * 
	 * @param deviceId
	 * @param index
	 * @param isWorking
	 */
	public void updatePlugIsWorking(int deviceId, int index, boolean isWorking) {

		var crite = Criteria.where("_id").is(getPlugInfoMongoId(deviceId, index));

		var update = Update.update("isWorking", isWorking).set("lastUpTime", new Date())
				.set("deviceId", deviceId)
				.set("deviceIndex",index);

		mongoTemplate.upsert(new Query(crite), update, PlugInfoMongoEntity.class);
	}

	/**
	 * ȡ�ò�ͷ��Ϣͨ���豸ID��������
	 * 
	 * @param deviceId
	 * @param index
	 * @return
	 * @throws ScanElectricityException
	 */
	public StationPlugInfo getPlugByDeviceIdAndIndex(int deviceId, int index) throws ScanElectricityException {
		var pluginfo = QStationPlugInfo.stationPlugInfo;
		return this.findAllItems(query -> {
			return query.where(pluginfo.deviceid.eq(deviceId)).where(pluginfo.deviceindex.eq(index));
		}).blockFirst();
	}
	
	/**
	 * ���ò�ͷ�ʵ���
	 * @param billCode
	 * @param deviceId
	 * @param index
	 */
	public void setPlugBillIdByDeviceIdAndIndex(long billId,int deviceId, int index) {
		var crite = Criteria.where("_id").is(getPlugInfoMongoId(deviceId, index));

		var update = Update.update("curBillId", billId).set("lastUpTime", new Date())
				.set("deviceId", deviceId)
				;

		mongoTemplate.upsert(new Query(crite), update, PlugInfoMongoEntity.class);
	}
	
	/**
	 * ȡ�ò�ͷ��ǰ�Ķ�����
	 * @param deviceId
	 * @param index
	 * @return
	 */
	public Integer getPlugBillIdByByDeviceIdAndIndex(int deviceId, int index) {
		var crite = Criteria.where("_id").is(getPlugInfoMongoId(deviceId, index));
		var entity=mongoTemplate.findOne(new Query(crite), PlugInfoMongoEntity.class);
		return entity.getCurBillId();
	}
	
	/**
	 * ���ò�ͷ�ʵ�����
	 * @param billCode
	 * @throws ScanElectricityException
	 */
	public void finishPlugBillCode(long billId) throws ScanElectricityException {
				
		var crite = Criteria.where("curBillId").is(billId);
		
		//ȡ�ø��ʵ����ڵĲ�ͷ���豸��Ϣ
		var bill=mongoTemplate.findOne(new Query(crite),PlugInfoMongoEntity.class);
		
		Assert.notNull(bill, String.format("���Ϊ(%d)�ʵ���Ϣû���ҵ�", billId));
		
		var update=Update.update("curBillId", null).set("lastUpTime", new Date());
		
		
		var res=mongoTemplate.upsert(new Query(crite), update, PlugInfoMongoEntity.class);
		if(res.getModifiedCount()<=0) throw new ScanElectricityException("�����ʵ�"+billId+"ʧ��,���ݿ�û���ʵ���¼");
	}

	/**
	 * ͨ��codeȡ�� plug
	 * 
	 * @param code
	 * @return
	 * @throws ScanElectricityException
	 */
	public StationPlugInfo findItemByCode(String code) throws ScanElectricityException {
		var pluginfo = QStationPlugInfo.stationPlugInfo;
		return this.findAllItems(query -> {
			return query.where(pluginfo.code.eq(code));
		}).blockFirst();
	}
}
