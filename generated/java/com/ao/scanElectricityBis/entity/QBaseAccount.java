package com.ao.scanElectricityBis.entity;

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

    private static final long serialVersionUID = -1451605644L;

    public static final QBaseAccount baseAccount = new QBaseAccount("baseAccount");

    public final QBaseBisEntity _super = new QBaseBisEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    //inherited
    public final NumberPath<Integer> creator = _super.creator;

    //inherited
    public final NumberPath<Integer> id = _super.id;

    //inherited
    public final NumberPath<Integer> modifier = _super.modifier;

    //inherited
    public final DateTimePath<java.util.Date> moditime = _super.moditime;

    public final NumberPath<Integer> operatorId = createNumber("operatorId", Integer.class);

    public final StringPath phone = createString("phone");

    public final StringPath pwd = createString("pwd");

    public final StringPath regionCode = createString("regionCode");

    public final BooleanPath status = createBoolean("status");

    public final StringPath userName = createString("userName");

    //inherited
    public final NumberPath<Integer> version = _super.version;

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

