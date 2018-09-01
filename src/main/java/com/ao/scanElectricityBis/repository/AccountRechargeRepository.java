package com.ao.scanElectricityBis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.AccountRecharge;

/**
 * �û���ֵ��¼���ݲ���
 * @author aohanhe
 *
 */
@Repository
public interface AccountRechargeRepository extends JpaRepository<AccountRecharge, Integer>,QuerydslPredicateExecutor<AccountRecharge>{

}
