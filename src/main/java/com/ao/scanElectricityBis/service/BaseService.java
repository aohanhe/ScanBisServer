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
 * ���������࣬�ṩ��ͨ��bean��ѯ�Ĺ���
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
	 * ȡ�òֿ��������
	 * @return
	 */
	public Repository getRep() {
		return rep;
	}
	
	/**
	 * ͨ��Id ȡ���û���Ϣ
	 * @param id
	 * @return
	 */
	public T findItemById(Object id) {
		var re=rep.findById(id);
		if(!re.isPresent()) return null;
		return (T) re.get();		
	}

	/**
	 * ȡ�ò�ѯ�Ż�������
	 * @return
	 */
	protected String getQueryGrapName() {
		return null;
	}
	
	/**
	 * ͨ����ѯbean������ѯ
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
	 * ���������ݵ����ݿ�
	 * @param item
	 * @return
	 */	
	protected T onSaveNew(T item) {
		
		return (T) rep.save(item);	
		
	}
	
	/**
	 * �������ݵ����ݿ�
	 * @param item
	 * @return
	 * @throws ScanElectricityException
	 */
	public  T saveNew(T item) throws ScanElectricityException {
		try {
			if (logger.isDebugEnabled())
				logger.debug("�����ݿⱣ���µļ�¼:" + item.toString());
			

			return onSaveNew(item);			
			
		} catch (Exception ex) {
			String strFomat = "�����ݿⱣ���µļ�¼%s ʧ��:%s";
			if (ex.getCause() instanceof MySQLIntegrityConstraintViolationException) {
				strFomat = "�����ݿⱣ���µļ�¼%s ʧ��:�ظ��ļ�ֵ:%s";
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
	 * ִ�и��¶������ݿ�
	 * @param item
	 * @return
	 */
	protected T onSave(T item) {
		
		return (T) rep.save(item);
	}
	
	/**
	 * ��������
	 * @param item
	 * @return
	 * @throws ScanElectricityException
	 */	
	public T saveItem(T item) throws ScanElectricityException {
		try {
			if (logger.isDebugEnabled())
				logger.debug("�����ݿ���¼�¼:" + item.toString());
			
			return onSave(item);			

		} catch (Exception ex) {
			String message = String.format("�����ݿ���¼�¼%s ʧ��:%s", item.toString(), ex.getMessage());
			logger.debug(message, ex);
			throw new ScanElectricityException(message, ex);
		}

	}
	
	
	/**
	 * ����ɾ������
	 * @param id
	 * @throws ScanElectricityException 
	 */
	protected void onDeleteItemById(int id) throws ScanElectricityException {
		rep.deleteById(id);		
	}
	
	/**
	 * ɾ������ById
	 * 
	 * @param id
	 * @throws ScanElectricityException
	 */
	public void deleteItemById(int id) throws ScanElectricityException {
		try {
			if (logger.isDebugEnabled())
				logger.debug("ɾ�����ݿ��¼��ID:" + id);
			onDeleteItemById(id);

		} catch (Exception ex) {
			String message = String.format("ɾ�����ݿ��¼%d ʧ��:%s", id, ex.getMessage());
			logger.debug(message, ex);
			throw new ScanElectricityException(message, ex);
		}
	}

}
