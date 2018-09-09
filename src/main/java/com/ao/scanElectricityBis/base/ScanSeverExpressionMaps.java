package com.ao.scanElectricityBis.base;



import javax.persistence.Entity;

import org.apache.logging.log4j.util.Strings;

import com.ao.QueryDslRootPaths;
import com.ao.scanElectricityBis.entity.BaseBisEntity;
import com.ao.scanElectricityBis.entity.QBaseAccount;
import com.ao.scanElectricityBis.entity.QUserInfo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import ao.jpaQueryHelper.SelectExpressionCollection;

public class ScanSeverExpressionMaps <T> extends SelectExpressionCollection<T>{
	private boolean isDoExtends=false;
	private static final  QBaseAccount accountCreator=QueryDslRootPaths.creatorAccount;
	private static final  QBaseAccount accountModif=QueryDslRootPaths.modifAccount;
	private Class<T> classType;
	private String name;

	public ScanSeverExpressionMaps(Expression mainExpression,Class<T> classType) {		
		super(mainExpression, classType);
		this.classType=classType;
		if(BaseBisEntity.class.isAssignableFrom(classType))
			isDoExtends=true;
		
		name = classType.getSimpleName();
		Entity entityDef = classType.getAnnotation(Entity.class);
		if (entityDef != null && !Strings.isBlank(entityDef.name()))
			name = entityDef.name();
	}

	@Override
	protected Expression[] getExtendsExpression() {
		if(!isDoExtends) return null;
		
		
		return new Expression[] {accountCreator.userName,accountModif.userName};
	}

	@Override
	protected void onFectionExtendsDataItem(Object item, Tuple tuple) {
		if(!isDoExtends) return;
		
		BaseBisEntity entity=(BaseBisEntity)item;
		
		entity.setCreatorName(tuple.get(accountCreator.userName));
		entity.setModifierName(tuple.get(accountModif.userName));				 
	}

	

	/**
	 * 添加扩展的查询条件
	 */
	@Override
	protected JPAQuery<Tuple> onAddExtendsLeftJoin(JPAQuery<Tuple> query) {
		if(isDoExtends) {			
			
			var creatorPath=Expressions.path(Integer.class,(Path) this.mainExpression, "creator");
			var modifPath=Expressions.path(Integer.class, (Path) this.mainExpression, "modifier");
			
			return query.leftJoin(accountCreator).on(creatorPath.eq(accountCreator.id))
				.leftJoin(accountModif).on(modifPath.eq(accountModif.id));			
		}
		return query;
		
	}

}
