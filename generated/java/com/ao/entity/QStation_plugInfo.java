package com.ao.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStation_plugInfo is a Querydsl query type for Station_plugInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStation_plugInfo extends EntityPathBase<Station_plugInfo> {

    private static final long serialVersionUID = 2015791007L;

    public static final QStation_plugInfo station_plugInfo = new QStation_plugInfo("station_plugInfo");

    public final StringPath code = createString("code");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final NumberPath<Integer> deviceId = createNumber("deviceId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.util.Date> moditime = createDateTime("moditime", java.util.Date.class);

    public QStation_plugInfo(String variable) {
        super(Station_plugInfo.class, forVariable(variable));
    }

    public QStation_plugInfo(Path<? extends Station_plugInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStation_plugInfo(PathMetadata metadata) {
        super(Station_plugInfo.class, metadata);
    }

}

