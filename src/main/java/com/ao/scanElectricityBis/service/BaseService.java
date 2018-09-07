package com.ao.scanElectricityBis.service;

import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanElectricityKeyException;
import com.ao.scanElectricityBis.entity.BaseOnlyIdEntity;
import com.mongodb.Mongo;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ao.jpaQueryHelper.BaseJpaQueryBean;
import ao.jpaQueryHelper.JpaQueryHelper;
import ao.jpaQueryHelper.JpaQueryHelperException;
import ao.jpaQueryHelper.PageInfo;
import ao.jpaQueryHelper.PageJpaQueryBean;
import ao.jpaQueryHelper.PagerResult;
import ao.jpaQueryHelper.QueryDslQueryHelper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ���������࣬�ṩ��ͨ��bean��ѯ�Ĺ���
 * 
 * @author aohanhe
 *
 * @param <T>
 */
public class BaseService<T extends BaseOnlyIdEntity, Repository extends JpaRepository> {
	private Logger logger = LoggerFactory.getLogger(BaseService.class);

	@Autowired
	protected EntityManager em;

	@Autowired
	protected Repository rep;

	/**
	 * querydsl��������
	 */
	protected JPAQueryFactory factory;

	@PostConstruct
	public void init() {
		factory = new JPAQueryFactory(em);
	}

	/**
	 * ͨ��IDȡ�ö���
	 * 
	 * @param id
	 * @return
	 * @throws ScanElectricityException
	 */
	public Mono<T> findItemById(int id, Class<? extends BaseOnlyIdEntity> classType) throws ScanElectricityException {
		String name = classType.getName();
		Entity entityDef = classType.getAnnotation(Entity.class);
		if (entityDef != null && !Strings.isBlank(entityDef.name()))
			name = entityDef.name();

		Path entityPath = Expressions.path(classType, name);
		Path idPath = Expressions.path(Integer.class, entityPath, "id");

		var item = this.createFullDslQuery().where(Expressions.predicate(Ops.EQ, idPath, Expressions.constant(id)))
				.fetchOne();

		if (item != null)
			return Mono.just(item).map(this::fecthTupleIntoEntity);

		return null;
	}

	/**
	 * ��ѯ���������������б�
	 * 
	 * @param initQuery
	 * @return
	 * @throws ScanElectricityException
	 */
	public Flux<T> findAllItems(Function<JPAQuery<Tuple>, JPAQuery<Tuple>> initQuery) throws ScanElectricityException {
		var query = this.createFullDslQuery();

		if (initQuery != null)
			query = initQuery.apply(query);

		return Flux.fromIterable(query.fetch()).map(this::fecthTupleIntoEntity);
	}

	/**
	 * ��ҳ��ѯ��������
	 * @param pager
	 * @param initQuery
	 * @return
	 * @throws ScanElectricityException
	 */
	public PagerResult<T> findAllItemsByPage(PageInfo pager, Function<JPAQuery<Tuple>, JPAQuery<Tuple>> initQuery)
			throws ScanElectricityException {
		var query = this.createFullDslQuery();

		if (initQuery != null)
			query = initQuery.apply(query);

		var res = query.offset((pager.getPage() - 1) * pager.getSize()).limit(pager.getSize()).fetchResults();
		var list = Flux.fromIterable(res.getResults()).map(this::fecthTupleIntoEntity);

		var pageResult = new PagerResult<T>(pager, res.getTotal(), list);

		return pageResult;
	}

	/**
	 * ��������dslȫ��ѯ�Ĳ�ѯ����
	 * 
	 * @return
	 * @throws ScanElectricityException
	 */
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		throw new ScanElectricityException("createFullDslQuery����û��ʵ��");
	}

	/**
	 * ��tuple�е�ֵд��ʵ�����
	 * 
	 * @param tuple
	 * @return
	 * @throws ScanElectricityException
	 */
	protected T fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {
		throw new RuntimeException("createFullDslQuery����û��ʵ��");
	}

	/**
	 * ���������ݵ����ݿ�
	 * 
	 * @param item
	 * @return
	 * @throws ScanElectricityException
	 */
	protected T onSaveNew(T item) throws ScanElectricityException {

		var reItem = rep.save(item);

		var re = this.findItemById(item.getId(), item.getClass());
		if (re == null)
			return null;

		return re.block();
	}

	/**
	 * �������ݵ����ݿ�
	 * 
	 * @param item
	 * @return
	 * @throws ScanElectricityException
	 */
	public T saveNew(T item) throws ScanElectricityException {
		try {
			if (logger.isDebugEnabled())
				logger.debug("�����ݿⱣ���µļ�¼:" + item.toString());

			var reItem = onSaveNew(item);

			var re = this.findItemById(item.getId(), item.getClass());
			if (re == null)
				return null;

			return re.block();

		} catch (Exception ex) {
			String strFomat = "�����ݿⱣ���µļ�¼%s ʧ��:%s";
			/*if (ex.getCause() instanceof MySQLIntegrityConstraintViolationException) {
				strFomat = "�����ݿⱣ���µļ�¼%s ʧ��:�ظ��ļ�ֵ:%s";
				String message = String.format(strFomat, item.toString(), ex.getMessage());
				logger.debug(message, ex);
				throw new ScanElectricityKeyException(message, ex);
			}
			*/
			String message = String.format(strFomat, item.toString(), ex.getMessage());
			logger.debug(message, ex);
			throw new ScanElectricityException(message, ex);
		}

	}

	/**
	 * ִ�и��¶������ݿ�
	 * 
	 * @param item
	 * @return
	 * @throws ScanElectricityException
	 */
	protected T onSave(T item) throws ScanElectricityException {

		var reItem = rep.save(item);
		var re = this.findItemById(item.getId(), item.getClass());
		if (re == null)
			return null;
		return re.block();
	}

	/**
	 * ��������
	 * 
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
	 * 
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

	/**
	 * ͨ����ѯbean��ѯ������������������
	 * 
	 * @param queryBean
	 * @return
	 * @throws ScanElectricityException
	 */
	public Flux<T> findAllByQueryBean(BaseJpaQueryBean queryBean) throws ScanElectricityException {

		try {
			var query = this.createFullDslQuery();
			var list = QueryDslQueryHelper.initPredicateAndSortFromQueryBean(query, queryBean).fetch();

			return Flux.fromIterable(list).map(this::fecthTupleIntoEntity);

		} catch (Exception e) {
			logger.error("��ѯ���ݳ���:" + e.getMessage(), e);
			throw new ScanElectricityException("��ѯ���ݳ���:" + e.getMessage(), e);
		}
	}

	/**
	 * ͨ����ҳ��ѯBeanȡ�ý��
	 * 
	 * @param queryBean
	 * @return
	 * @throws ScanElectricityException
	 */
	public PagerResult<T> findAllByQueryBeanByPage(PageJpaQueryBean queryBean) throws ScanElectricityException {
		try {
			var query = this.createFullDslQuery();
			var res = QueryDslQueryHelper.initPredicateAndSortFromQueryBean(query, queryBean).fetchResults();
			var list = Flux.fromIterable(res.getResults()).map(this::fecthTupleIntoEntity);

			var pageResult = new PagerResult<T>(queryBean.getPager(), res.getTotal(), list);

			return pageResult;

		} catch (Exception e) {
			logger.error("��ѯ���ݳ���:" + e.getMessage(), e);
			throw new ScanElectricityException("��ѯ���ݳ���:" + e.getMessage(), e);
		}
	}

}
