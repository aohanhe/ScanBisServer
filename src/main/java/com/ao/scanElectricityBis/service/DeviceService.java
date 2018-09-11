package com.ao.scanElectricityBis.service;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanSeverExpressionMaps;
import com.ao.scanElectricityBis.entity.BaseAccount;
import com.ao.scanElectricityBis.entity.QBaseOperator;
import com.ao.scanElectricityBis.entity.QStationDevice;
import com.ao.scanElectricityBis.entity.QStationStationInfo;
import com.ao.scanElectricityBis.entity.StationDevice;
import com.ao.scanElectricityBis.repository.DeviceRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * �豸����
 * 
 * @author aohanhe
 *
 */
@Service
public class DeviceService extends BaseService<StationDevice, DeviceRepository> {
	private Logger logger=LoggerFactory.getLogger(DeviceService.class);
	
	@Autowired
	private EntityManager em;

	public DeviceService() {
		super(QStationDevice.stationDevice);
	}
	
	

	/**
	 * ͨ��IDȡ�ö���
	 * 
	 * @param code
	 * @return
	 * @throws ScanElectricityException
	 */
	public StationDevice findItemByCode(String code) throws ScanElectricityException {
		var device = QStationDevice.stationDevice;

		return this.findAllItems(v -> {
			return v.where(device.code.eq(code));
		}).blockFirst();
	}
	
	
	private ScanSeverExpressionMaps<StationDevice> selectLists;
	
	@Override
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		var device=QStationDevice.stationDevice;
		var station=QStationStationInfo.stationStationInfo;
		var operator=QBaseOperator.baseOperator;
		
		try {

			if (selectLists == null) {
				selectLists = new ScanSeverExpressionMaps<>(device, StationDevice.class);
				selectLists.putItem("operator", operator.name);
				selectLists.putItem("operatorid", station.operatorid);
				selectLists.putItem("regionCode", station.regioncode);				
				selectLists.putItem("stationName", station.name);
				selectLists.putItem("price", station.price);
				selectLists.putItem("sharingScale", station.sharingScale);

			}
			return selectLists.addExtendsLeftJoin(factory.select(selectLists.getExpressionArray()).from(device)
					.leftJoin(station).on(device.stationId.eq(station.id))
					.leftJoin(operator).on(station.operatorid.eq(operator.id)));
		} catch (Exception ex) {
			logger.error("������ѯָ�����:" + ex.getMessage(), ex);
			throw new ScanElectricityException("������ѯָ�����:" + ex.getMessage(), ex);
		}
		
	}
	
	@Override
	protected StationDevice fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {
		try {
			return selectLists.fectionDataInItem(tuple);
		} catch (IllegalAccessException e) {
			logger.error("�ӽ������ȡ����ʧ��:" + e.getMessage(), e);
			throw new RuntimeException("�ӽ������ȡ����ʧ��:" + e.getMessage(), e);
		}
	}
	
		
	
	
}
