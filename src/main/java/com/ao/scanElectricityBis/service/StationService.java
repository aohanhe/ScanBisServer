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
 * 站场服务类
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
	 * 重写保存数据操作，以同步mongodb操作
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
	 * 重写新增操作，以实现与mongodb的同步
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
	 * 删除站场信息
	 */
	@Override
	protected void onDeleteItemById(int id) throws ScanElectricityException {		
		super.onDeleteItemById(id);
		//同时删除mongodb中对应的值 
		mongoRep.deleteById(id);		
	}
	
	/**
	 * 查找指定点的附近的所有站点
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
	 * 取得站场的空闲插座数
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
			logger.error("创建查询条件失败:" + ex.getMessage(), ex);
			throw new ScanElectricityException("创建查询条件失败:" + ex.getMessage(), ex);
		}
	}
	
	@Override
	protected StationStationInfo fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {
		
		try {
			return selectList.fectionDataInItem(tuple);
		} catch (IllegalAccessException ex) {
			logger.error("处理查询失败:" + ex.getMessage(), ex);
			throw new RuntimeException("处理查询失败:" + ex.getMessage(), ex);
		}
	}

}
