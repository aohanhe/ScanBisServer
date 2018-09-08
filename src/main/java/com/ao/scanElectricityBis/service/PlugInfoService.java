package com.ao.scanElectricityBis.service;


import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanSeverExpressionMaps;
import com.ao.scanElectricityBis.entity.QBaseOperator;
import com.ao.scanElectricityBis.entity.QStationDevice;
import com.ao.scanElectricityBis.entity.QStationPlugInfo;
import com.ao.scanElectricityBis.entity.QStationStationInfo;
import com.ao.scanElectricityBis.entity.StationPlugInfo;
import com.ao.scanElectricityBis.repository.PlugInfoRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

import ao.jpaQueryHelper.SelectExpressionCollection;

public class PlugInfoService extends BaseService<StationPlugInfo, PlugInfoRepository>{
	private Logger logger=LoggerFactory.getLogger(PlugInfoService.class);
	
	@Autowired
	private EntityManager em;

	private ScanSeverExpressionMaps<StationPlugInfo> selectList;
	
	public PlugInfoService() {
		super(QStationPlugInfo.stationPlugInfo);
	}
	
	@Override
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		var plugInfo=QStationPlugInfo.stationPlugInfo;
		var device=QStationDevice.stationDevice;
		var station=QStationStationInfo.stationStationInfo;
		var operator=QBaseOperator.baseOperator;
		
		try {
			if(selectList==null) {
				selectList=new ScanSeverExpressionMaps<StationPlugInfo>(plugInfo, StationPlugInfo.class);
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
			
			var query= factory.select(selectList.getExpressionArray())
				.from(plugInfo)
				.leftJoin(device).on(plugInfo.deviceid.eq(device.id))
				.leftJoin(station).on(device.stationId.eq(station.id))
				.leftJoin(operator).on(station.operatorid.eq(operator.id));
			
			return selectList.addExtendsLeftJoin(query);
			
		}catch(Exception ex) {
			logger.error("创建查询条件失败:" + ex.getMessage(), ex);
			throw new ScanElectricityException("创建查询条件失败:" + ex.getMessage(), ex);
		}
		
	}
	
	@Override
	protected StationPlugInfo fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {
		
		try {
			return selectList.fectionDataInItem(tuple);
		} catch (IllegalAccessException ex) {
			logger.error("处理查询失败:" + ex.getMessage(), ex);
			throw new RuntimeException("处理查询失败:" + ex.getMessage(), ex);
		}
	}
	
	

}
