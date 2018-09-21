package com.ao.scanElectricityBis.service;

import static org.assertj.core.api.Assertions.from;

import java.time.Duration;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import javax.transaction.Transactional;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanSeverExpressionMaps;
import com.ao.scanElectricityBis.entity.AccountExpense;
import com.ao.scanElectricityBis.entity.AccountExpenseMongoEntity;

import com.ao.scanElectricityBis.entity.QAccountExpense;
import com.ao.scanElectricityBis.entity.QBaseOperator;
import com.ao.scanElectricityBis.entity.QStationDevice;
import com.ao.scanElectricityBis.entity.QStationPlugInfo;
import com.ao.scanElectricityBis.entity.QStationStationInfo;
import com.ao.scanElectricityBis.entity.QUserInfo;
import com.ao.scanElectricityBis.entity.StationDevice;
import com.ao.scanElectricityBis.entity.UserInfo;
import com.ao.scanElectricityBis.repository.AccountExpenseRepository;

import com.mysema.commons.lang.Pair;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * 消费记录管理类
 * 
 * @author aohanhe
 *
 */
@Service
public class ExpensesService extends BaseService<AccountExpense, AccountExpenseRepository> {
	private Logger logger = LoggerFactory.getLogger(ExpensesService.class);

	private static final String CollectionName = "UserExpenses";

	@Autowired
	private EntityManager em;

	private ScanSeverExpressionMaps<AccountExpense> selectList;

	public ExpensesService() {
		super(QAccountExpense.accountExpense);
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	@PostConstruct
	public void initMogoDb() {

		if (mongoTemplate.collectionExists(CollectionName))
			return;

		// 如果消费记录列表还没有创建，则创建它
		CollectionOptions ops = CollectionOptions.empty().capped().maxDocuments(10000);

		var collection = mongoTemplate.createCollection(CollectionName, ops);

		var index = Document.parse("{id:1}");
		collection.createIndex(index);

	}

	@Override
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		var expense = QAccountExpense.accountExpense;
		var device = QStationDevice.stationDevice;
		var pluginfo = QStationPlugInfo.stationPlugInfo;
		var userInfo = QUserInfo.userInfo;
		var station = QStationStationInfo.stationStationInfo;
		var operator = QBaseOperator.baseOperator;

		try {

			if (selectList == null) {
				selectList = new ScanSeverExpressionMaps<>(expense, AccountExpense.class);
				selectList.putItem("userName", userInfo.name);
				selectList.putItem("phone", userInfo.phone);
				selectList.putItem("deviceId", device.id);
				selectList.putItem("devicecode", device.code);
				selectList.putItem("station", station.name);
				selectList.putItem("stationId", station.id);
				selectList.putItem("regionCode", station.regioncode);
				selectList.putItem("operatorId", station.operatorid);
				selectList.putItem("operator", operator.name);
			}

			var selects = selectList.getExpressionArray();
			var query = factory.select(selects).from(expense).leftJoin(pluginfo).on(expense.plugid.eq(pluginfo.id))
					.leftJoin(device).on(pluginfo.deviceid.eq(device.id)).leftJoin(station)
					.on(device.stationId.eq(station.id)).leftJoin(operator).on(station.operatorid.eq(operator.id))
					.leftJoin(userInfo).on(expense.userId.eq(userInfo.id));

			return selectList.addExtendsLeftJoin(query);

		} catch (Exception ex) {
			logger.error("创建查询指令出错:" + ex.getMessage(), ex);
			throw new ScanElectricityException("创建查询指令出错:" + ex.getMessage(), ex);
		}

	}

	@Override
	protected AccountExpense fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {

		try {
			return selectList.fectionDataInItem(tuple);
		} catch (IllegalAccessException e) {
			logger.error("从结果中提取数据失败:" + e.getMessage(), e);
			throw new RuntimeException("从结果中提取数据失败:" + e.getMessage(), e);
		}
	}

	/**
	 * 取得用户的消费总时间及总的花费
	 * 
	 * @param id
	 * @return
	 */
	public Pair<Float, Integer> getTotalTimeAndMoney(int id) {
		var expense = QAccountExpense.accountExpense;
		var res = factory.select(expense.cost.sum(), expense.costminute.sum()).from(expense)
				.where(expense.userId.eq(id)).fetchOne();

		return Pair.of(res.get(0, Float.class), res.get(1, Integer.class));
	}

	@Override
	protected void onDeleteItemById(int id) throws ScanElectricityException {
		throw new ScanElectricityException("不支持直接删除消费记录");
	}

	public JPAQuery<Tuple> getFullQuery() {
		var expense = QAccountExpense.accountExpense;
		var userInfo = QUserInfo.userInfo;

		return factory.select(expense, userInfo.name).from(expense).leftJoin(userInfo)
				.on(expense.userId.eq(userInfo.id));
	}

	public AccountExpense fectchDataItem(Tuple tuple) {
		var expense = QAccountExpense.accountExpense;
		var userInfo = QUserInfo.userInfo;

		AccountExpense item = tuple.get(expense);
		item.setUserName(tuple.get(userInfo.name));

		return item;
	}

	@Autowired
	private UsersService usersService;

	@Autowired
	private PlugInfoService plugInfoService;

	@Autowired
	private DeviceService deviceService;

	/**
	 * 创建用户的充电帐单 记录用户已经开始进行充电
	 * 
	 * @param deviceId
	 * @param index
	 * @param userId
	 * @param planMinute
	 * @return 创建的充电帐号编号
	 * @throws ScanElectricityException
	 */
	@Transactional
	public int createExpenseBill(int deviceId, int index, int userId, int planMinute) throws ScanElectricityException {
		// 1 取得用户的帐户信息
		var userInfo = usersService.findItemById(userId, UserInfo.class).block();
		var plugInfo = plugInfoService.getPlugByDeviceIdAndIndex(deviceId, index);
		var deviceInfo = deviceService.findItemById(deviceId, StationDevice.class).block();

		if (userInfo == null)
			throw new ScanElectricityException(String.format("用户ID=%d的信息没有找到", userId));
		if (deviceInfo == null)
			throw new ScanElectricityException(String.format("设备ID=%d的信息没有找到", deviceId));
		
		//检查用户帐户是否足够余额
		var cost=planMinute*deviceInfo.getPrice();
		if(cost>userInfo.getMoney())
			throw new ScanElectricityException("用户帐户余额不足");

		// 创建帐单信息
		Date now = new Date();
		AccountExpense expense = new AccountExpense();
		expense.setBeforeMoney(userInfo.getMoney());
		expense.setCostminute(planMinute);
		expense.setUserId(userId);
		expense.setDeviceId(deviceId);
		expense.setPlugid(plugInfo.getId());
		expense.setCost(0);
		expense.setStartDate(null);

		expense = this.saveItem(expense);

		// 在mongodb中创建对应的信息
		AccountExpenseMongoEntity entity = new AccountExpenseMongoEntity();
		entity.setId(expense.getId());
		entity.setBeforeMoney(userInfo.getMoney());
		entity.setUserId(userId);
		entity.setCostminute(0);
		entity.setStartDate(null);
		entity.setPlugid(plugInfo.getId());
		
		entity.setPlanMinute(planMinute);
		entity.setLastUpdateTime(now);
		entity.setPrice(deviceInfo.getPrice());

		mongoTemplate.insert(entity);

		return expense.getId();
	}
	
	/**
	 * 设置帐单开始充电
	 * @param billId
	 */
	public void upExpenseBillStart(int billId) {
		var query = Criteria.where("id").is(billId);
		var update = Update.update("lastUpdateTime", new Date())
				.set("startDate", new Date());
		mongoTemplate.updateFirst(new Query(query), update, AccountExpenseMongoEntity.class,
				ExpensesService.CollectionName);
		
		var expense = QAccountExpense.accountExpense;
		var res=factory.update(expense)
			.set(expense.startDate,new Date())
			.where(expense.id.eq(billId))
			.execute();
		Assert.isTrue(res==1, "更新帐单失败,没有更新到记录");
	}

	/**
	 * 更新帐单的状态
	 * 
	 * @param billId
	 * @param costMinute
	 * @throws ScanElectricityException
	 */
	public AccountExpenseMongoEntity upExpenseBillCost(int billId, int costMinute) throws ScanElectricityException {

		// 取得原始订单的单价
		var query = Criteria.where("id").is(billId);

		var billEntity = mongoTemplate.findOne(new Query(query), AccountExpenseMongoEntity.class,
				ExpensesService.CollectionName);

		if (billEntity == null) {
			throw new ScanElectricityException(String.format("编号为%d的帐单没有找到", billId));
		}

		// 计算消费金额
		float cost = billEntity.getPrice() * costMinute;

		// 更新帐单的状态
		var update = Update.update("lastUpdateTime", new Date()).inc("costminute", costMinute).inc("cost", cost);

		mongoTemplate.updateFirst(new Query(query), update, AccountExpenseMongoEntity.class,
				ExpensesService.CollectionName);
		return mongoTemplate.findOne(new Query(query), AccountExpenseMongoEntity.class, ExpensesService.CollectionName);
	}

	/**
	 * 完成订单
	 * 
	 * @param billId
	 * @param finishDate
	 * @return
	 * @throws ScanElectricityException
	 */
	public AccountExpense finishExpenseBill(int billId, Integer costMinute) throws ScanElectricityException {
		// 取得原始订单的单价
		var query = Criteria.where("id").is(billId);

		var billEntity = mongoTemplate.findOne(new Query(query), AccountExpenseMongoEntity.class,
				ExpensesService.CollectionName);

		if (billEntity == null) {
			throw new ScanElectricityException(String.format("编号为%d的帐单没有找到", billId));
		}
		
		if(costMinute==null)
			costMinute = (int) Duration.between(billEntity.getStartDate().toInstant(), (new Date()).toInstant()).toMinutes();
		float cost = costMinute * billEntity.getPrice();

		var update = Update.update("lastUpdateTime", new Date())
				.set("cost", cost)
				.set("costminute", costMinute)
				.set("isFinish", true);
		
		//更新mongodb数据库
		mongoTemplate.updateFirst(new Query(query), update, AccountExpenseMongoEntity.class,
				ExpensesService.CollectionName);
		
		//更新数据库订单信息
		var expense = QAccountExpense.accountExpense;
			
		
		var res=factory.update(expense)
			.set(expense.afterMoney, expense.beforeMoney.add(-cost))
			.set(expense.cost, cost)
			.set(expense.costminute,(int)costMinute)
			.set(expense.finishDate, new Date())			
			.where(expense.id.eq(billId))			
			.execute();
		
		if(res<=0)
			throw new ScanElectricityException("完成帐单失败，没有数据被更新到");
		
		return this.findItemById(billId, AccountExpense.class).block();
	}

}
