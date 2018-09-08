package com.ao.scanElectricityBis.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountExpense is a Querydsl query type for AccountExpense
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAccountExpense extends EntityPathBase<AccountExpense> {

    private static final long serialVersionUID = -700229869L;

    public static final QAccountExpense accountExpense = new QAccountExpense("accountExpense");

    public final QBaseOnlyIdEntity _super = new QBaseOnlyIdEntity(this);

    public final NumberPath<Float> afterMoney = createNumber("afterMoney", Float.class);

    public final NumberPath<Float> beforeMoney = createNumber("beforeMoney", Float.class);

    public final NumberPath<Float> cost = createNumber("cost", Float.class);

    public final NumberPath<Integer> costminute = createNumber("costminute", Integer.class);

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final NumberPath<Integer> plugid = createNumber("plugid", Integer.class);

    public final NumberPath<Integer> userid = createNumber("userid", Integer.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QAccountExpense(String variable) {
        super(AccountExpense.class, forVariable(variable));
    }

    public QAccountExpense(Path<? extends AccountExpense> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountExpense(PathMetadata metadata) {
        super(AccountExpense.class, metadata);
    }

}

