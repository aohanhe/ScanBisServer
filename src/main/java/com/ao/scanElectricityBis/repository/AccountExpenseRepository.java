package com.ao.scanElectricityBis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.AccountExpense;

/**
 * 用户消费记录存贮类
 * @author aohanhe
 *
 */
@Repository
public interface AccountExpenseRepository extends JpaRepository<AccountExpense, Integer>,QuerydslPredicateExecutor<AccountExpense>{

}
