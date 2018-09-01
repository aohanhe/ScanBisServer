package com.ao.scanElectricityBis.service;



import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.entity.AccountExpense;
import com.ao.scanElectricityBis.entity.QAccountExpense;
import com.ao.scanElectricityBis.repository.AccountExpenseRepository;
import com.mysema.commons.lang.Pair;
import com.querydsl.jpa.impl.JPAQueryFactory;



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
	
	/**
	 * querydsl构建工具
	 */
	private JPAQueryFactory factory;

	@PostConstruct
	public void init() {
		factory = new JPAQueryFactory(em);
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

}
