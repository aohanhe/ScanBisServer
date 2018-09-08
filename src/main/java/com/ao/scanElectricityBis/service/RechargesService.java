package com.ao.scanElectricityBis.service;

import java.util.function.Function;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanSeverExpressionMaps;
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
 * 充值服务类
 * 
 * @author aohanhe
 *
 */
@Service
public class RechargesService extends BaseService<AccountRecharge, AccountRechargeRepository> {
	private Logger logger = LoggerFactory.getLogger(RechargesService.class);

	@Autowired
	private EntityManager em;

	private ScanSeverExpressionMaps<AccountRecharge> selectList;
	
	public RechargesService() {
		super(QAccountRecharge.accountRecharge);
	}

	@Override
	protected void onDeleteItemById(int id) throws ScanElectricityException {
		throw new ScanElectricityException("不支持直接删除充值记录");
	}

	@Override
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		var userInfo = QUserInfo.userInfo;
		var recharge = QAccountRecharge.accountRecharge;
		

		try {
			if (selectList == null) {
				selectList = new ScanSeverExpressionMaps<AccountRecharge>(recharge, AccountRecharge.class);
				selectList.putItem("username", userInfo.name);
				selectList.putItem("phone", userInfo.phone);

			}

			var query = factory.select(selectList.getExpressionArray()).from(recharge)
					.leftJoin(userInfo).on(recharge.userid.eq(userInfo.id));
			
			return selectList.addExtendsLeftJoin(query);

		} catch (Exception ex) {
			logger.error("创建查询条件失败:" + ex.getMessage(), ex);
			throw new ScanElectricityException("创建查询条件失败:" + ex.getMessage(), ex);
		}

	}

	@Override
	protected AccountRecharge fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {
		try {
			return selectList.fectionDataInItem(tuple);
		} catch (IllegalAccessException ex) {
			logger.error("处理查询失败:" + ex.getMessage(), ex);
			throw new RuntimeException("处理查询失败:" + ex.getMessage(), ex);
		}
	}

}
