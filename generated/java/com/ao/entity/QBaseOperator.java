package com.ao.entity;

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

    private static final long serialVersionUID = 1100549165L;

    public static final QBaseOperator baseOperator = new QBaseOperator("baseOperator");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final NumberPath<Integer> creator = createNumber("creator", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> modifier = createNumber("modifier", Integer.class);

    public final DateTimePath<java.util.Date> moditime = createDateTime("moditime", java.util.Date.class);

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

