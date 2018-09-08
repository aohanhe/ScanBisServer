package com.ao.scanElectricityBis.service;



import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanSeverExpressionMaps;
import com.ao.scanElectricityBis.entity.AccountExpense;
import com.ao.scanElectricityBis.entity.QAccountExpense;
import com.ao.scanElectricityBis.entity.QBaseOperator;
import com.ao.scanElectricityBis.entity.QStationDevice;
import com.ao.scanElectricityBis.entity.QStationPlugInfo;
import com.ao.scanElectricityBis.entity.QStationStationInfo;
import com.ao.scanElectricityBis.entity.QUserInfo;
import com.ao.scanElectricityBis.repository.AccountExpenseRepository;
import com.mysema.commons.lang.Pair;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ao.jpaQueryHelper.SelectExpressionCollection;



/**
 * 消费记录管理类
 * @author aohanhe
 *
 */
@Service
public class ExpensesService extends BaseService<AccountExpense,AccountExpenseRepository> {
	private Logger logger= LoggerFactory.getLogger(ExpensesService.class);
	
	@Autowired
	private EntityManager em;
	
	
	private ScanSeverExpressionMaps<AccountExpense> selectList;
	
	public ExpensesService() {
		super(QAccountExpense.accountExpense);
	}
	
	
	@Override
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		var expense=QAccountExpense.accountExpense;
		var device=QStationDevice.stationDevice;
		var pluginfo=QStationPlugInfo.stationPlugInfo;
		var userInfo=QUserInfo.userInfo;
		var station=QStationStationInfo.stationStationInfo;
		var operator=QBaseOperator.baseOperator;
		
		try {
		
		if(selectList==null) {
			selectList=new ScanSeverExpressionMaps<>(expense, AccountExpense.class);
			selectList.putItem("name", userInfo.name);
			selectList.putItem("phone", userInfo.phone);
			selectList.putItem("deviceId", device.id);
			selectList.putItem("devicecode", device.code);
			selectList.putItem("station", station.name);
			selectList.putItem("stationId", station.id);
			selectList.putItem("regioncode", station.regioncode);
			selectList.putItem("operatorId", station.operatorid);
			selectList.putItem("operator", operator.name);
		}		
		
		var selects=selectList.getExpressionArray();
		var query=factory.select(selects)				
				.from(expense)
				.leftJoin(pluginfo).on(expense.plugid.eq(pluginfo.id))
				.leftJoin(device).on(pluginfo.deviceid.eq(device.id))
				.leftJoin(station).on(device.stationId.eq(station.id))
				.leftJoin(operator).on(station.operatorid.eq(operator.id))
				.leftJoin(userInfo).on(expense.userid.eq(userInfo.id));
		
		return selectList.addExtendsLeftJoin(query);
		
		}catch(Exception ex) {
			logger.error("创建查询指令出错:"+ex.getMessage(),ex);
			throw new ScanElectricityException("创建查询指令出错:"+ex.getMessage(),ex);
		}
		
	}
	
	@Override
	protected AccountExpense fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {
				
		try {
			return selectList.fectionDataInItem(tuple);
		} catch (IllegalAccessException e) {
			logger.error("从结果中提取数据失败:"+e.getMessage(),e);
			throw new RuntimeException("从结果中提取数据失败:"+e.getMessage(),e);
		}
	}
	

	
	/**
	 * 取得用户的消费总时间及总的花费
	 * @param id
	 * @return
	 */
	public Pair<Float, Integer> getTotalTimeAndMoney(int id) {
		var expense=QAccountExpense.accountExpense;
		var res=factory.select(expense.cost.sum(),expense.costminute.sum()).from(expense)
		       .where(expense.userid.eq(id)).fetchOne();
		
		return Pair.of(res.get(expense.cost), res.get(expense.costminute));
	}
	
	@Override
	protected void onDeleteItemById(int id) throws ScanElectricityException {
		throw new ScanElectricityException("不支持直接删除消费记录");	
	}
	
	
	
	
	
	
	public JPAQuery<Tuple> getFullQuery(){
		var expense=QAccountExpense.accountExpense;
		var userInfo=QUserInfo.userInfo;
		
		return factory.select(expense,userInfo.name)
				.from(expense).leftJoin(userInfo)
				.on(expense.userid.eq(userInfo.id));
	}
	
	public AccountExpense fectchDataItem(Tuple tuple) {
		var expense=QAccountExpense.accountExpense;
		var userInfo=QUserInfo.userInfo;
		
		AccountExpense item=tuple.get(expense);
		item.setUserName(tuple.get(userInfo.name));
		
		return item;		
	}

}
