package com.ao.scanElectricityBis.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanSeverExpressionMaps;
import com.ao.scanElectricityBis.entity.QBaseOperator;
import com.ao.scanElectricityBis.entity.QStationDevice;
import com.ao.scanElectricityBis.entity.QStationStationInfo;
import com.ao.scanElectricityBis.entity.StationDevice;
import com.ao.scanElectricityBis.entity.StationMongoEntry;
import com.ao.scanElectricityBis.entity.StationPlugInfo;
import com.ao.scanElectricityBis.entity.StationMongoEntry.Pos;
import com.ao.scanElectricityBis.entity.StationStationInfo;
import com.ao.scanElectricityBis.repository.StationEntryMongoRepository;
import com.ao.scanElectricityBis.repository.StationRepositiory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ao.jpaQueryHelper.BaseJpaQueryBean;
import ao.jpaQueryHelper.JpaQueryHelper;
import ao.jpaQueryHelper.JpaQueryHelperException;
import ao.jpaQueryHelper.SelectExpressionCollection;
import reactor.core.publisher.Flux;

/**
 * վ��������
 * 
 * @author aohanhe
 *
 */
@Service
public class StationService extends BaseService<StationStationInfo, StationRepositiory> {
	private Logger logger = LoggerFactory.getLogger(StationService.class);
	@Autowired
	private EntityManager em;
	@Autowired
	private StationEntryMongoRepository mongoRep;

	public StationService() {
		super(QStationStationInfo.stationStationInfo);
	}
	
	/**
	 * ��д�������ݲ�������ͬ��mongodb����
	 */
	@Override
	protected StationStationInfo onSave(StationStationInfo item) {
		item=this.rep.save(item);
		StationMongoEntry mongItem=new StationMongoEntry();
		
		mongItem.setStationId(item.getId());		
		mongItem.setName(item.getName());
		mongItem.setAddress(item.getAddress());
		mongItem.setPrice(item.getPrice());

		String[] strPos = item.getPoint().split(",");
		mongItem.setPos(new Pos(Double.parseDouble(strPos[0]),Double.parseDouble(strPos[1])));
		
		mongoRep.save(mongItem);
		
		return item;
	}
	
	/**
	 * ��д������������ʵ����mongodb��ͬ��
	 */
	@Override
	protected StationStationInfo onSaveNew(StationStationInfo item) {
		item=this.rep.save(item);
		StationMongoEntry mongItem=new StationMongoEntry();
		
		mongItem.setStationId(item.getId());		
		mongItem.setName(item.getName());
		mongItem.setAddress(item.getAddress());
		mongItem.setPrice(item.getPrice());

		String[] strPos = item.getPoint().split(",");
		mongItem.setPos(new Pos(Double.parseDouble(strPos[0]),Double.parseDouble(strPos[1])));
		
		mongoRep.insert(mongItem);		
		return item;
	}
	
	/**
	 * ɾ��վ����Ϣ
	 */
	@Override
	protected void onDeleteItemById(int id) throws ScanElectricityException {		
		super.onDeleteItemById(id);
		//ͬʱɾ��mongodb�ж�Ӧ��ֵ 
		mongoRep.deleteById(id);		
	}
	
	/**
	 * ����ָ����ĸ���������վ��
	 * @param pos
	 * @return
	 */
	public List<StationStationInfo>  listNearPosStations(double lon,double lat){
		var reList = this.mongoRep.findByPos( lon,lat);
		if(reList==null) return null;
		ArrayList<StationStationInfo> re=new ArrayList<>();
		
		return Flux.fromIterable(reList)
			.map(item->{
				var rItem=new StationStationInfo();
				rItem.setId(item.getStationId());
				rItem.setAddress(item.getAddress());
				rItem.setPoint(item.getPos().toString());
				rItem.setName(item.getName());
				rItem.setPrice(item.getPrice());
				return rItem;
			}).collectList().block()
		;
	}
	
	/**
	 * ȡ��վ���Ŀ��в�����
	 * @param id
	 * @return
	 */
	public int FreePlugerNumber(int id) {
		var device=QStationDevice.stationDevice;
		var station=QStationStationInfo.stationStationInfo;
		
		var item=factory.select(device.totalNumber.sum(),device.usingNumber.sum(),device.faultNumber.sum())
			   .from(device)
			   .leftJoin(station).on(device.stationId.eq(station.id))			   					   
			   .groupBy(station.id)
			   .where(station.id.eq(id))
			   .fetchOne();		
		
		
		if(item==null) return 0;	
		
		return item.get(0, Integer.class)-item.get(1, Integer.class)-item.get(2, Integer.class);
		
	}
	
	private ScanSeverExpressionMaps<StationStationInfo> selectList;
	
	@Override
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		var station=QStationStationInfo.stationStationInfo;
		var operator=QBaseOperator.baseOperator;
		
		try {
			if (selectList == null) {
				selectList=new ScanSeverExpressionMaps<>(station,StationStationInfo.class);
				selectList.putItem("operator", operator.name);
			}
			
			var query= factory.select(selectList.getExpressionArray())
					.from(station)
					.leftJoin(operator).on(station.operatorid.eq(operator.id))
					;
				
				return selectList.addExtendsLeftJoin(query);
		}catch(Exception ex) {
			logger.error("������ѯ����ʧ��:" + ex.getMessage(), ex);
			throw new ScanElectricityException("������ѯ����ʧ��:" + ex.getMessage(), ex);
		}
	}
	
	@Override
	protected StationStationInfo fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {
		
		try {
			return selectList.fectionDataInItem(tuple);
		} catch (IllegalAccessException ex) {
			logger.error("�����ѯʧ��:" + ex.getMessage(), ex);
			throw new RuntimeException("�����ѯʧ��:" + ex.getMessage(), ex);
		}
	}

}
