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
 * �ʺŷ�����
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
	 * querydsl��������
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
	 * ͨ���û���ȡ���û���Ϣ
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
	 * ����û��ʺ������Ƿ���ȷ
	 * 
	 * @param userName
	 * @param pwd
	 * @return �������ȷ���ؿ�
	 * @throws ScanElectricityException
	 */
	public BaseAccount checkUserPwd(String userName, String pwd) throws ScanElectricityException {

		if (Strings.isBlank(userName))
			throw new ScanElectricityException("���� userName����Ϊ��");
		if (Strings.isBlank(pwd))
			throw new ScanElectricityException("���� pwd����Ϊ��");

		var account = QBaseAccount.baseAccount;
		return factory.selectFrom(account)
				.where(account.userName.eq(userName).and(account.pwd.eq(pwd)).and(account.status.eq(true))).fetchOne();
	}

	/**
	 * �����ѯ����
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
			logger.error("������ѯָ�����:" + ex.getMessage(), ex);
			throw new ScanElectricityException("������ѯָ�����:" + ex.getMessage(), ex);
		}
	}

	@Override
	protected BaseAccount fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {
		try {
			return selectLists.fectionDataInItem(tuple);
		} catch (IllegalAccessException e) {
			logger.error("�ӽ������ȡ����ʧ��:" + e.getMessage(), e);
			throw new RuntimeException("�ӽ������ȡ����ʧ��:" + e.getMessage(), e);
		}
	}
	
	/**
	 * �����û��ĵ绰������
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
			
		if(res<1) throw new ScanElectricityException("����ʧ�ܣ�û�м�¼������");
	}
	

}
