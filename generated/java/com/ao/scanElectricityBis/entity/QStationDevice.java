package com.ao.scanElectricityBis.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStationDevice is a Querydsl query type for StationDevice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStationDevice extends EntityPathBase<StationDevice> {

    private static final long serialVersionUID = 2045846018L;

    public static final QStationDevice stationDevice = new QStationDevice("stationDevice");

    public final QBaseBisEntity _super = new QBaseBisEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.util.Date> created = _super.created;

    //inherited
    public final NumberPath<Integer> creator = _super.creator;

    public final NumberPath<Integer> faultNumber = createNumber("faultNumber", Integer.class);

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final StringPath localAdress = createString("localAdress");

    public final StringPath localPoint = createString("localPoint");

    //inherited
    public final NumberPath<Integer> modifier = _super.modifier;

    //inherited
    public final DateTimePath<java.util.Date> moditime = _super.moditime;

    public final NumberPath<Integer> stationId = createNumber("stationId", Integer.class);

    public final NumberPath<Byte> status = createNumber("status", Byte.class);

    public final NumberPath<Integer> totalNumber = createNumber("totalNumber", Integer.class);

    public final NumberPath<Integer> usingNumber = createNumber("usingNumber", Integer.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QStationDevice(String variable) {
        super(StationDevice.class, forVariable(variable));
    }

    public QStationDevice(Path<? extends StationDevice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStationDevice(PathMetadata metadata) {
        super(StationDevice.class, metadata);
    }

}

