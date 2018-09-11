package com.ao.scanElectricityBis.service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanSeverExpressionMaps;
import com.ao.scanElectricityBis.entity.BaseOperator;
import com.ao.scanElectricityBis.entity.QBaseOperator;
import com.ao.scanElectricityBis.entity.StationDevice;
import com.ao.scanElectricityBis.repository.OperatorsRepositiory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

import ao.jpaQueryHelper.BaseJpaQueryBean;
import ao.jpaQueryHelper.JpaQueryHelper;
import ao.jpaQueryHelper.JpaQueryHelperException;
import io.swagger.annotations.ApiOperation;

/**
 * 运营商服务类
 * 
 * @author aohanhe
 *
 */
@Service
public class OperatorsService extends BaseService<BaseOperator, OperatorsRepositiory> {
	private static Logger logger = LoggerFactory.getLogger(OperatorsService.class);
	@Autowired
	private EntityManager em;

	public OperatorsService() {
		super(QBaseOperator.baseOperator);
	}

	private ScanSeverExpressionMaps<BaseOperator> selectLists;

	@Override
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		var operator = QBaseOperator.baseOperator;
		try {

			if (selectLists == null) {
				selectLists = new ScanSeverExpressionMaps<>(operator, BaseOperator.class);

			}
			return selectLists.addExtendsLeftJoin(factory.select(selectLists.getExpressionArray()).from(operator));
		} catch (Exception ex) {
			logger.error("创建查询指令出错:" + ex.getMessage(), ex);
			throw new ScanElectricityException("创建查询指令出错:" + ex.getMessage(), ex);
		}
	}

	@Override
	protected BaseOperator fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {
		try {
			return selectLists.fectionDataInItem(tuple);
		} catch (IllegalAccessException e) {
			logger.error("从结果中提取数据失败:" + e.getMessage(), e);
			throw new RuntimeException("从结果中提取数据失败:" + e.getMessage(), e);
		}
	}
	
	

}
