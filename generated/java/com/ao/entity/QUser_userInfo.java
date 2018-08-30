package com.ao.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser_userInfo is a Querydsl query type for User_userInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser_userInfo extends EntityPathBase<User_userInfo> {

    private static final long serialVersionUID = -1914070411L;

    public static final QUser_userInfo user_userInfo = new QUser_userInfo("user_userInfo");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final NumberPath<Integer> creator = createNumber("creator", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.util.Date> lastAcess = createDateTime("lastAcess", java.util.Date.class);

    public final NumberPath<Integer> modifier = createNumber("modifier", Integer.class);

    public final DateTimePath<java.util.Date> moditime = createDateTime("moditime", java.util.Date.class);

    public final NumberPath<Float> money = createNumber("money", Float.class);

    public final StringPath openid = createString("openid");

    public final StringPath phone = createString("phone");

    public final StringPath pwd = createString("pwd");

    public final StringPath userName = createString("userName");

    public QUser_userInfo(String variable) {
        super(User_userInfo.class, forVariable(variable));
    }

    public QUser_userInfo(Path<? extends User_userInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser_userInfo(PathMetadata metadata) {
        super(User_userInfo.class, metadata);
    }

}

