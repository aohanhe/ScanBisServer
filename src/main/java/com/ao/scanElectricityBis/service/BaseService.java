package com.ao.scanElectricityBis.service;

import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanElectricityKeyException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import ao.jpaQueryHelper.BaseJpaQueryBean;
import ao.jpaQueryHelper.JpaQueryHelper;
import ao.jpaQueryHelper.JpaQueryHelperException;

/**
 * 基础服务类，提供了通过bean查询的功能
 * @author aohanhe
 *
 * @param <T>
 */
public  class BaseService <T,Repository extends JpaRepository> {
	private Logger logger= LoggerFactory.getLogger(BaseService.class);
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private Repository rep;
	
	

	/**
	 * 取得仓库操作对象
	 * @return
	 */
	public Repository getRep() {
		return rep;
	}
	
	/**
	 * 通过Id 取得用户信息
	 * @param id
	 * @return
	 */
	public T findItemById(Object id) {
		var re=rep.findById(id);
		if(!re.isPresent()) return null;
		return (T) re.get();		
	}

	/**
	 * 取得查询优化的名称
	 * @return
	 */
	protected String getQueryGrapName() {
		return null;
	}
	
	/**
	 * 通过查询bean构建查询
	 * @param query
	 * @param reClassType
	 * @return
	 * @throws JpaQueryHelperException
	 */
	public TypedQuery<T> findAllByQueryBean(BaseJpaQueryBean query,Class<T> reClassType) throws JpaQueryHelperException{
		String queryGrap=getQueryGrapName();
		if(!Strings.isBlank(queryGrap))
			return JpaQueryHelper.createQueryFromBean(em, query, queryGrap,null, reClassType);
		return JpaQueryHelper.createQueryFromBean(em, query,  reClassType);
	}
	
	/**
	 * 处理保存数据到数据库
	 * @param item
	 * @return
	 */	
	protected T onSaveNew(T item) {
		
		return (T) rep.save(item);	
		
	}
	
	/**
	 * 保存数据到数据库
	 * @param item
	 * @return
	 * @throws ScanElectricityException
	 */
	public  T saveNew(T item) throws ScanElectricityException {
		try {
			if (logger.isDebugEnabled())
				logger.debug("向数据库保存新的记录:" + item.toString());
			

			return onSaveNew(item);			
			
		} catch (Exception ex) {
			String strFomat = "向数据库保存新的记录%s 失败:%s";
			if (ex.getCause() instanceof MySQLIntegrityConstraintViolationException) {
				strFomat = "向数据库保存新的记录%s 失败:重复的键值:%s";
				String message = String.format(strFomat, item.toString(), ex.getMessage());
				logger.debug(message, ex);
				throw new ScanElectricityKeyException(message, ex);
			}
			String message = String.format(strFomat, item.toString(), ex.getMessage());
			logger.debug(message, ex);
			throw new ScanElectricityException(message, ex);
		}

	}
	
	/**
	 * 执行更新对象到数据库
	 * @param item
	 * @return
	 */
	protected T onSave(T item) {
		
		return (T) rep.save(item);
	}
	
	/**
	 * 更新数据
	 * @param item
	 * @return
	 * @throws ScanElectricityException
	 */	
	public T saveItem(T item) throws ScanElectricityException {
		try {
			if (logger.isDebugEnabled())
				logger.debug("向数据库更新记录:" + item.toString());
			
			return onSave(item);			

		} catch (Exception ex) {
			String message = String.format("向数据库更新记录%s 失败:%s", item.toString(), ex.getMessage());
			logger.debug(message, ex);
			throw new ScanElectricityException(message, ex);
		}

	}
	
	
	/**
	 * 处理删除操作
	 * @param id
	 * @throws ScanElectricityException 
	 */
	protected void onDeleteItemById(int id) throws ScanElectricityException {
		rep.deleteById(id);		
	}
	
	/**
	 * 删除对象ById
	 * 
	 * @param id
	 * @throws ScanElectricityException
	 */
	public void deleteItemById(int id) throws ScanElectricityException {
		try {
			if (logger.isDebugEnabled())
				logger.debug("删除数据库记录，ID:" + id);
			onDeleteItemById(id);

		} catch (Exception ex) {
			String message = String.format("删除数据库记录%d 失败:%s", id, ex.getMessage());
			logger.debug(message, ex);
			throw new ScanElectricityException(message, ex);
		}
	}

}
