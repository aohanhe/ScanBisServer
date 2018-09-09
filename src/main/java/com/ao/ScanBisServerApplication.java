package com.ao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.entity.AccountExpense;
import com.ao.scanElectricityBis.entity.QAccountExpense;
import com.ao.scanElectricityBis.entity.QUserInfo;
import com.ao.scanElectricityBis.service.ExpensesService;
import com.querydsl.jpa.impl.JPAQueryFactory;



@SpringBootApplication
public class ScanBisServerApplication {
	@Autowired
	private EntityManager em;
	
	@Autowired
	private ExpensesService service;
	
	/**
	 * querydsl构建工具
	 */
	protected JPAQueryFactory factory;

	@PostConstruct
	public void init() {
		factory = new JPAQueryFactory(em);
	}
	
	public static void main(String[] args) throws ScanElectricityException {
		var context = SpringApplication.run(ScanBisServerApplication.class, args);
		var app=context.getBean(ScanBisServerApplication.class);
		
		app.test4();
	}
	
	public void test1() throws ScanElectricityException {
		service.findAllItems(query->{
			var expense=QAccountExpense.accountExpense;
			
			return query.where(expense.id.gt(0)).orderBy(expense.id.desc());
			
		}).subscribe(v->{
			System.out.print(v.getId());
			System.out.print(v.getUserName());
			System.out.print(v.getStation());
			System.out.print(v.getDevicecode());
			System.out.println(v.getOperator());
		});
	}
	
	public void test2() {
		var expense=QAccountExpense.accountExpense;
		
		var list = factory.select(expense)
			.from(expense)
			.where(expense.id.gt(0))
			.where(expense.id.lt(1))
			
			.fetch()
			;
		
		list.forEach(System.out::println);
		
		
	}
	
	public void test3() {
		var expense=QAccountExpense.accountExpense;
		var userInfo = QUserInfo.userInfo;
		
		var res=factory.select(expense.id,userInfo.name)
		       .from(expense)
		       .leftJoin(userInfo).on(expense.userId.eq(userInfo.id))
		       .fetch();
		
		res.forEach(v->{
			System.out.println(v.get(userInfo.name));
		});
		
	}
	
	public void test4() {
		var expense=QAccountExpense.accountExpense;
		var test2=new QAccountExpense("test2");
		var res=factory.select(test2.id,expense.userId)
				.from(expense)
				.leftJoin(test2).on(expense.id.eq(test2.id))
				
				.fetch();
		
		res.forEach(v->{
			System.out.println(v.get(test2.id));
		});
		
		
	}

}
