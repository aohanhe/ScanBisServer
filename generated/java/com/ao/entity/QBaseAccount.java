package com.ao.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseAccount is a Querydsl query type for BaseAccount
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBaseAccount extends EntityPathBase<BaseAccount> {

    private static final long serialVersionUID = -1264217724L;

    public static final QBaseAccount baseAccount = new QBaseAccount("baseAccount");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final NumberPath<Integer> creator = createNumber("creator", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> modifier = createNumber("modifier", Integer.class);

    public final DateTimePath<java.util.Date> moditime = createDateTime("moditime", java.util.Date.class);

    public final NumberPath<Integer> operatorId = createNumber("operatorId", Integer.class);

    public final StringPath phone = createString("phone");

    public final StringPath pwd = createString("pwd");

    public final StringPath regionCode = createString("regionCode");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath userName = createString("userName");

    public QBaseAccount(String variable) {
        super(BaseAccount.class, forVariable(variable));
    }

    public QBaseAccount(Path<? extends BaseAccount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseAccount(PathMetadata metadata) {
        super(BaseAccount.class, metadata);
    }

}

