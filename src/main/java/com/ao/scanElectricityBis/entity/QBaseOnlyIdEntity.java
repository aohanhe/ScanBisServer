package com.ao.scanElectricityBis.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseOnlyIdEntity is a Querydsl query type for BaseOnlyIdEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseOnlyIdEntity extends EntityPathBase<BaseOnlyIdEntity> {

    private static final long serialVersionUID = -521981501L;

    public static final QBaseOnlyIdEntity baseOnlyIdEntity = new QBaseOnlyIdEntity("baseOnlyIdEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QBaseOnlyIdEntity(String variable) {
        super(BaseOnlyIdEntity.class, forVariable(variable));
    }

    public QBaseOnlyIdEntity(Path<? extends BaseOnlyIdEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseOnlyIdEntity(PathMetadata metadata) {
        super(BaseOnlyIdEntity.class, metadata);
    }

}

