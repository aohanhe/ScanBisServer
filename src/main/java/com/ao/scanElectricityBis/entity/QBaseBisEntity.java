package com.ao.scanElectricityBis.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseBisEntity is a Querydsl query type for BaseBisEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseBisEntity extends EntityPathBase<BaseBisEntity> {

    private static final long serialVersionUID = 1037260182L;

    public static final QBaseBisEntity baseBisEntity = new QBaseBisEntity("baseBisEntity");

    public final QBaseOnlyIdEntity _super = new QBaseOnlyIdEntity(this);

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final NumberPath<Integer> creator = createNumber("creator", Integer.class);

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final NumberPath<Integer> modifier = createNumber("modifier", Integer.class);

    public final DateTimePath<java.util.Date> moditime = createDateTime("moditime", java.util.Date.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QBaseBisEntity(String variable) {
        super(BaseBisEntity.class, forVariable(variable));
    }

    public QBaseBisEntity(Path<? extends BaseBisEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseBisEntity(PathMetadata metadata) {
        super(BaseBisEntity.class, metadata);
    }

}

