package com.ao.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountRecharge is a Querydsl query type for AccountRecharge
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAccountRecharge extends EntityPathBase<AccountRecharge> {

    private static final long serialVersionUID = 458919772L;

    public static final QAccountRecharge accountRecharge = new QAccountRecharge("accountRecharge");

    public final NumberPath<Float> afterMoney = createNumber("afterMoney", Float.class);

    public final NumberPath<Float> beforeMoney = createNumber("beforeMoney", Float.class);

    public final NumberPath<Float> charge = createNumber("charge", Float.class);

    public final StringPath code = createString("code");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final NumberPath<Integer> creator = createNumber("creator", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> modifier = createNumber("modifier", Integer.class);

    public final DateTimePath<java.util.Date> moditime = createDateTime("moditime", java.util.Date.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> userid = createNumber("userid", Integer.class);

    public QAccountRecharge(String variable) {
        super(AccountRecharge.class, forVariable(variable));
    }

    public QAccountRecharge(Path<? extends AccountRecharge> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountRecharge(PathMetadata metadata) {
        super(AccountRecharge.class, metadata);
    }

}

