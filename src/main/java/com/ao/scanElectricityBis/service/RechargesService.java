package com.ao.scanElectricityBis.service;

import java.util.function.Function;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.entity.AccountExpense;
import com.ao.scanElectricityBis.entity.AccountRecharge;
import com.ao.scanElectricityBis.entity.QAccountRecharge;
import com.ao.scanElectricityBis.entity.QBaseAccount;
import com.ao.scanElectricityBis.entity.QUserInfo;
import com.ao.scanElectricityBis.repository.AccountRechargeRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

import ao.jpaQueryHelper.PageInfo;
import ao.jpaQueryHelper.PagerResult;
import ao.jpaQueryHelper.SelectExpressionCollection;

/**
 * ��ֵ������
 * 
 * @author aohanhe
 *
 */
@Service
public class RechargesService extends BaseService<AccountRecharge, AccountRechargeRepository> {
	private Logger logger = LoggerFactory.getLogger(RechargesService.class);

	@Autowired
	private EntityManager em;

	private SelectExpressionCollection<AccountRecharge> selectList;

	@Override
	protected void onDeleteItemById(int id) throws ScanElectricityException {
		throw new ScanElectricityException("��֧��ֱ��ɾ����ֵ��¼");
	}

	@Override
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		var userInfo = QUserInfo.userInfo;
		var recharge = QAccountRecharge.accountRecharge;
		var account1 = QBaseAccount.baseAccount;
		var account2 = new QBaseAccount("account2");

		try {
			if (selectList == null) {
				selectList = new SelectExpressionCollection<AccountRecharge>(recharge, AccountRecharge.class);
				selectList.putItem("username", userInfo.userName);
				selectList.putItem("phone", userInfo.phone);

				selectList.putItem("creatorName", account1.userName);
				selectList.putItem("modifierName", account2.userName);
			}

			return factory.select(selectList.getExpressionArray()).from(recharge)
					.leftJoin(userInfo).on(recharge.userid.eq(userInfo.id))
					.leftJoin(account1).on(recharge.creator.eq(account1.id))
					.leftJoin(account2).on(recharge.modifier.eq(account2.id));

		} catch (Exception ex) {
			logger.error("������ѯ����ʧ��:" + ex.getMessage(), ex);
			throw new ScanElectricityException("������ѯ����ʧ��:" + ex.getMessage(), ex);
		}

	}

	@Override
	protected AccountRecharge fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {
		try {
			return selectList.fectionDataInItem(tuple);
		} catch (IllegalAccessException ex) {
			logger.error("�����ѯʧ��:" + ex.getMessage(), ex);
			throw new RuntimeException("�����ѯʧ��:" + ex.getMessage(), ex);
		}
	}

}
