package com.ao.scanElectricityBis.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseOperator is a Querydsl query type for BaseOperator
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBaseOperator extends EntityPathBase<BaseOperator> {

    private static final long serialVersionUID = -413509059L;

    public static final QBaseOperator baseOperator = new QBaseOperator("baseOperator");

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

    public final StringPath name = createString("name");

    public QBaseOperator(String variable) {
        super(BaseOperator.class, forVariable(variable));
    }

    public QBaseOperator(Path<? extends BaseOperator> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseOperator(PathMetadata metadata) {
        super(BaseOperator.class, metadata);
    }

}

