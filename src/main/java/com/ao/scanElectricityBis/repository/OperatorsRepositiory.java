package com.ao.scanElectricityBis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.BaseOperator;

/**
 * ��Ӫ���������ݲ�����
 * @author aohanhe
 *
 */
@Repository
public interface OperatorsRepositiory extends JpaRepository<BaseOperator, Integer>,QuerydslPredicateExecutor<BaseOperator>{

}
