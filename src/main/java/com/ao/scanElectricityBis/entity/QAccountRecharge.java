package com.ao.scanElectricityBis.entity;

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

    private static final long serialVersionUID = 896911180L;

    public static final QAccountRecharge accountRecharge = new QAccountRecharge("accountRecharge");

    public final QBaseBisEntity _super = new QBaseBisEntity(this);

    public final NumberPath<Float> afterMoney = createNumber("afterMoney", Float.class);

    public final NumberPath<Float> beforeMoney = createNumber("beforeMoney", Float.class);

    public final NumberPath<Float> charge = createNumber("charge", Float.class);

    public final StringPath code = createString("code");

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

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> userid = createNumber("userid", Integer.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

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

