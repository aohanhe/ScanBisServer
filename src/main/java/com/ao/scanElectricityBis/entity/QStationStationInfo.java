package com.ao.scanElectricityBis.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStationStationInfo is a Querydsl query type for StationStationInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStationStationInfo extends EntityPathBase<StationStationInfo> {

    private static final long serialVersionUID = -934770730L;

    public static final QStationStationInfo stationStationInfo = new QStationStationInfo("stationStationInfo");

    public final QBaseBisEntity _super = new QBaseBisEntity(this);

    public final StringPath address = createString("address");

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

    public final NumberPath<Integer> operatorid = createNumber("operatorid", Integer.class);

    public final StringPath point = createString("point");

    public final NumberPath<Float> price = createNumber("price", Float.class);

    public final StringPath regioncode = createString("regioncode");

    public final NumberPath<Float> sharingScale = createNumber("sharingScale", Float.class);

    public final BooleanPath status = createBoolean("status");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QStationStationInfo(String variable) {
        super(StationStationInfo.class, forVariable(variable));
    }

    public QStationStationInfo(Path<? extends StationStationInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStationStationInfo(PathMetadata metadata) {
        super(StationStationInfo.class, metadata);
    }

}

