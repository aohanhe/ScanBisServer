package com.ao.scanElectricityBis.service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.entity.BaseOperator;
import com.ao.scanElectricityBis.repository.OperatorsRepositiory;

import ao.jpaQueryHelper.BaseJpaQueryBean;
import ao.jpaQueryHelper.JpaQueryHelper;
import ao.jpaQueryHelper.JpaQueryHelperException;

/**
 * 运营商服务类
 * @author aohanhe
 *
 */
@Service
public class OperatorsService extends BaseService<BaseOperator, OperatorsRepositiory>{
	@Autowired
	private EntityManager em;
	
	

}
