package com.ao.scanElectricityBis.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserInfo is a Querydsl query type for UserInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserInfo extends EntityPathBase<UserInfo> {

    private static final long serialVersionUID = -627170623L;

    public static final QUserInfo userInfo = new QUserInfo("userInfo");

    public final QBaseBisEntity _super = new QBaseBisEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    //inherited
    public final NumberPath<Integer> creator = _super.creator;

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final DateTimePath<java.util.Date> lastAccess = createDateTime("lastAccess", java.util.Date.class);

    //inherited
    public final NumberPath<Integer> modifier = _super.modifier;

    //inherited
    public final DateTimePath<java.util.Date> moditime = _super.moditime;

    public final NumberPath<Float> money = createNumber("money", Float.class);

    public final StringPath name = createString("name");

    public final StringPath openid = createString("openid");

    public final StringPath phone = createString("phone");

    public final StringPath pwd = createString("pwd");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QUserInfo(String variable) {
        super(UserInfo.class, forVariable(variable));
    }

    public QUserInfo(Path<? extends UserInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserInfo(PathMetadata metadata) {
        super(UserInfo.class, metadata);
    }

}

