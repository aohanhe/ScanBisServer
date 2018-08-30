package com.ao.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStation_stationInfo is a Querydsl query type for Station_stationInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStation_stationInfo extends EntityPathBase<Station_stationInfo> {

    private static final long serialVersionUID = -1129448225L;

    public static final QStation_stationInfo station_stationInfo = new QStation_stationInfo("station_stationInfo");

    public final StringPath address = createString("address");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final NumberPath<Integer> creator = createNumber("creator", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> modifier = createNumber("modifier", Integer.class);

    public final DateTimePath<java.util.Date> moditime = createDateTime("moditime", java.util.Date.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> operatorId = createNumber("operatorId", Integer.class);

    public final StringPath point = createString("point");

    public final NumberPath<Float> price = createNumber("price", Float.class);

    public final StringPath regioncode = createString("regioncode");

    public final NumberPath<Float> sharingScale = createNumber("sharingScale", Float.class);

    public final NumberPath<Byte> status = createNumber("status", Byte.class);

    public QStation_stationInfo(String variable) {
        super(Station_stationInfo.class, forVariable(variable));
    }

    public QStation_stationInfo(Path<? extends Station_stationInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStation_stationInfo(PathMetadata metadata) {
        super(Station_stationInfo.class, metadata);
    }

}

