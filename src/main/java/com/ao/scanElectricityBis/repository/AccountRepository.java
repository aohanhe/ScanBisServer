package com.ao.scanElectricityBis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.BaseAccount;

/**
 * �û��ʻ����ݿ����
 * @author aohanhe
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<BaseAccount, Integer>,QuerydslPredicateExecutor<BaseAccount>{

}
