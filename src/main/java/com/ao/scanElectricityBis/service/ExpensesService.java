package com.ao.scanElectricityBis.service;


import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import com.ao.OrderStates;
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
 * ���Ѽ�¼������
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

		// ������Ѽ�¼�б�û�д������򴴽���
		CollectionOptions ops = CollectionOptions.empty().maxDocuments(10000);

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
			logger.error("������ѯָ�����:" + ex.getMessage(), ex);
			throw new ScanElectricityException("������ѯָ�����:" + ex.getMessage(), ex);
		}

	}

	@Override
	protected AccountExpense fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {

		try {
			return selectList.fectionDataInItem(tuple);
		} catch (IllegalAccessException e) {
			logger.error("�ӽ������ȡ����ʧ��:" + e.getMessage(), e);
			throw new RuntimeException("�ӽ������ȡ����ʧ��:" + e.getMessage(), e);
		}
	}

	/**
	 * ȡ���û���������ʱ�估�ܵĻ���
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
		throw new ScanElectricityException("��֧��ֱ��ɾ�����Ѽ�¼");
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
	 * �����û��ĳ���ʵ� ��¼�û��Ѿ���ʼ���г��
	 * 
	 * @param deviceId
	 * @param index
	 * @param userId
	 * @param planMinute
	 * @return �����ĳ���ʺű��
	 * @throws ScanElectricityException
	 */
	@Transactional
	public int createExpenseBill(int deviceId, int index, int userId, int planMinute) throws ScanElectricityException {
		// 1 ȡ���û����ʻ���Ϣ
		var userMono = usersService.findItemById(userId, UserInfo.class);

		Assert.notNull(userMono, String.format("�û�ID(%d)������", userId));

		var userInfo = userMono.block();
		var plugInfo = plugInfoService.getPlugByDeviceIdAndIndex(deviceId, index);
		var deviceInfo = deviceService.findItemById(deviceId, StationDevice.class).block();

		if (userInfo == null)
			throw new ScanElectricityException(String.format("�û�ID=%d����Ϣû���ҵ�", userId));
		if (deviceInfo == null)
			throw new ScanElectricityException(String.format("�豸ID=%d����Ϣû���ҵ�", deviceId));

		// ����û��ʻ��Ƿ��㹻���
		var cost = planMinute * deviceInfo.getPrice() / 60;
		if (cost > userInfo.getMoney())
			throw new ScanElectricityException("�û��ʻ�����");

		// �����ʵ���Ϣ
		Date now = new Date();
		AccountExpense expense = new AccountExpense();
		expense.setBeforeMoney(userInfo.getMoney());
		expense.setCostminute(planMinute);
		expense.setUserId(userId);
		expense.setDeviceId(deviceId);
		expense.setPlugid(plugInfo.getId());
		expense.setCost(0);
		expense.setStartDate(null);
		expense.setStatus(OrderStates.PendingStart.getStateId());

		expense = this.saveItem(expense);

		// ��mongodb�д�����Ӧ����Ϣ
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
		entity.setStatus(OrderStates.PendingStart.getStateId()); //���ö�������ʼ

		mongoTemplate.insert(entity, ExpensesService.CollectionName);

		return expense.getId();
	}

	/**
	 * �����ʵ���ʼ���
	 * 
	 * @param billId
	 */
	@Transactional
	public void upExpenseBillStart(int billId) {
		var query = Criteria.where("_id").is(billId);
		var update = Update.update("lastUpdateTime", new Date()).set("startDate", new Date())
					.set("status", OrderStates.Charging.getStateId());
		mongoTemplate.updateFirst(new Query(query), update, AccountExpenseMongoEntity.class,
				ExpensesService.CollectionName);

		var expense = QAccountExpense.accountExpense;
		var res = factory.update(expense).set(expense.startDate, new Date()).where(expense.id.eq(billId)).execute();
		Assert.isTrue(res == 1, "�����ʵ�ʧ��,û�и��µ���¼");
	}
	
	/**
	 * ��ͣ���
	 * @param billId
	 */
	@Transactional
	public void pauseExpenseBill(int billId) {
		var query = Criteria.where("_id").is(billId);
		var update = Update.update("lastUpdateTime", new Date())
				.set("status", OrderStates.Pausing.getStateId());
		mongoTemplate.updateFirst(new Query(query), update, AccountExpenseMongoEntity.class,
				ExpensesService.CollectionName);

		var expense = QAccountExpense.accountExpense;
		var res = factory.update(expense).set(expense.startDate, new Date()).where(expense.id.eq(billId)).execute();
		Assert.isTrue(res == 1, "�����ʵ�ʧ��,û�и��µ���¼");
	}

	/**
	 * �����ʵ���״̬
	 * 
	 * @param billId
	 * @param costMinute
	 * @throws ScanElectricityException
	 */
	public AccountExpenseMongoEntity upExpenseBillCost(int billId, int costMinute) throws ScanElectricityException {

		// ȡ��ԭʼ�����ĵ���
		var query = Criteria.where("_id").is(billId);

		var billEntity = mongoTemplate.findOne(new Query(query), AccountExpenseMongoEntity.class,
				ExpensesService.CollectionName);

		if (billEntity == null) {
			throw new ScanElectricityException(String.format("���Ϊ%d���ʵ�û���ҵ�", billId));
		}

		// �������ѽ��
		float cost = billEntity.getPrice() * costMinute / 60;

		// �����ʵ���״̬
		var update = Update.update("lastUpdateTime", new Date()).inc("costminute", costMinute).inc("cost", cost);

		mongoTemplate.updateFirst(new Query(query), update, AccountExpenseMongoEntity.class,
				ExpensesService.CollectionName);

		// �������ݿⶩ����Ϣ
		var expense = QAccountExpense.accountExpense;

		var res = factory.update(expense).set(expense.afterMoney, expense.beforeMoney.add(-cost))
				.set(expense.cost, cost).set(expense.costminute, (int) costMinute).set(expense.finishDate, new Date())
				
				.where(expense.id.eq(billId)).execute();

		if (res <= 0)
			throw new ScanElectricityException("����ʵ�ʧ�ܣ�û�����ݱ����µ�");

		

		return mongoTemplate.findOne(new Query(query), AccountExpenseMongoEntity.class, ExpensesService.CollectionName);

	}

	/**
	 * ��ɶ���
	 * 
	 * @param billId
	 * @param finishDate
	 * @return
	 * @throws ScanElectricityException
	 */
	@Transactional
	public void finishExpenseBill(int billId, Integer costMinute) throws ScanElectricityException {
		// ȡ��ԭʼ�����ĵ���
		var query = Criteria.where("_id").is(billId);

		var billEntity = mongoTemplate.findOne(new Query(query), AccountExpenseMongoEntity.class,
				ExpensesService.CollectionName);

		if (billEntity == null) {
			throw new ScanElectricityException(String.format("���Ϊ%d���ʵ�û���ҵ�", billId));
		}

		if (costMinute == null)
			costMinute = (int) Duration.between(billEntity.getStartDate().toInstant(), (new Date()).toInstant())
					.toMinutes();
		float cost = costMinute * billEntity.getPrice() / 60;

		var update = Update.update("lastUpdateTime", new Date()).set("cost", cost).set("costminute", costMinute)
				.set("isFinish", true).set("status", OrderStates.Finished.getStateId());

		// ����mongodb���ݿ�
		mongoTemplate.updateFirst(new Query(query), update, AccountExpenseMongoEntity.class,
				ExpensesService.CollectionName);
		
		this.upExpenseBillCost(billId, costMinute);
		
		//�����ʵ�Ϊ����
		var exp=QAccountExpense.accountExpense;
		var res=factory.update(exp)
			.set(exp.status,OrderStates.Finished.getStateId())
			.where(exp.id.eq(billId))
			.execute();
			
		Assert.isTrue(res>0, String.format("����id=%d�ʵ�Ϊ����ʧ�ܣ�û���ʵ������µ�", billId));
	}
	
	/**
	 * ȡ�������豸ID��Ӧ��δ��ɶ��������б�
	 * @return
	 * @throws ScanElectricityException 
	 */
	public List<AccountExpense> getNotFinishItemByDeviceId(int deviceId) throws ScanElectricityException{
		var expense = QAccountExpense.accountExpense;
		var device=QStationDevice.stationDevice;
		
		return this.findAllItems(query->{
			return query.where(device.id.eq(deviceId))
					.where(expense.status.eq(OrderStates.Charging.getStateId()));
		}).collect(Collectors.toList()).block();
	}

}
