package com.ao.scanElectricityBis.service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanSeverExpressionMaps;
import com.ao.scanElectricityBis.entity.BaseAccount;
import com.ao.scanElectricityBis.entity.QBaseAccount;
import com.ao.scanElectricityBis.entity.QBaseOperator;
import com.ao.scanElectricityBis.repository.AccountRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ao.jpaQueryHelper.SelectExpressionCollection;

/**
 * 帐号服务类
 * 
 * @author aohanhe
 *
 */
@Service
@DependsOn("accountRepository")
public class AccountService extends BaseService<BaseAccount, AccountRepository> {
	private Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private EntityManager em;

	private ScanSeverExpressionMaps<BaseAccount> selectLists;

	/**
	 * querydsl构建工具
	 */
	private JPAQueryFactory factory;

	@PostConstruct
	public void init() {
		factory = new JPAQueryFactory(em);
	}
	
	public AccountService() {
		super(QBaseAccount.baseAccount);
	}

	/**
	 * 通过用户名取得用户信息
	 * 
	 * @param userName
	 * @return
	 * @throws ScanElectricityException
	 */
	public BaseAccount getUserByUserName(String userName) throws ScanElectricityException {
		var account = QBaseAccount.baseAccount;

		return this.findAllItems(pre -> {
			return pre.where(account.userName.eq(userName).and(account.status.eq(true)));
		}).blockFirst();
	}

	/**
	 * 检查用户帐号密码是否正确
	 * 
	 * @param userName
	 * @param pwd
	 * @return 如果不正确返回空
	 * @throws ScanElectricityException
	 */
	public BaseAccount checkUserPwd(String userName, String pwd) throws ScanElectricityException {

		if (Strings.isBlank(userName))
			throw new ScanElectricityException("参数 userName不能为空");
		if (Strings.isBlank(pwd))
			throw new ScanElectricityException("参数 pwd不能为空");

		var account = QBaseAccount.baseAccount;
		return factory.selectFrom(account)
				.where(account.userName.eq(userName).and(account.pwd.eq(pwd)).and(account.status.eq(true))).fetchOne();
	}

	/**
	 * 处理查询生成
	 */
	@Override
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		var account = QBaseAccount.baseAccount;
		var operator = QBaseOperator.baseOperator;

		try {

			if (selectLists == null) {
				selectLists = new ScanSeverExpressionMaps<>(account, BaseAccount.class);
				selectLists.putItem("operatorName", operator.name);

			}
			return  selectLists.addExtendsLeftJoin(
					factory.select(selectLists.getExpressionArray()).from(account).leftJoin(operator)
					.on(account.operatorId.eq(operator.id))
					
					);
		} catch (Exception ex) {
			logger.error("创建查询指令出错:" + ex.getMessage(), ex);
			throw new ScanElectricityException("创建查询指令出错:" + ex.getMessage(), ex);
		}
	}

	@Override
	protected BaseAccount fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {
		try {
			return selectLists.fectionDataInItem(tuple);
		} catch (IllegalAccessException e) {
			logger.error("从结果中提取数据失败:" + e.getMessage(), e);
			throw new RuntimeException("从结果中提取数据失败:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 更新用户的电话及密码
	 * @param userId
	 * @param phone
	 * @param pwd
	 * @throws ScanElectricityException
	 */
	@Transactional
	public void updateUserPwdAndPhone(int userId,String phone,String pwd) throws ScanElectricityException {
		var account = QBaseAccount.baseAccount;
		var res=factory.update(account)
			 .set(account.phone, phone)
			 .set(account.pwd,pwd)
			 .where(account.id.eq(userId))
			 .execute();
			
		if(res<1) throw new ScanElectricityException("更新失败，没有记录被更新");
	}
	

}
