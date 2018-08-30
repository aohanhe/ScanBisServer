package com.ao.entity;

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

    private static final long serialVersionUID = 1737010706L;

    public static final QStationDevice stationDevice = new QStationDevice("stationDevice");

    public final StringPath code = createString("code");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final NumberPath<Integer> creator = createNumber("creator", Integer.class);

    public final NumberPath<Integer> faultNumber = createNumber("faultNumber", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath localAdress = createString("localAdress");

    public final StringPath localPoint = createString("localPoint");

    public final NumberPath<Integer> modifier = createNumber("modifier", Integer.class);

    public final DateTimePath<java.util.Date> moditime = createDateTime("moditime", java.util.Date.class);

    public final NumberPath<Integer> stationId = createNumber("stationId", Integer.class);

    public final NumberPath<Byte> status = createNumber("status", Byte.class);

    public final NumberPath<Integer> totalNumber = createNumber("totalNumber", Integer.class);

    public final NumberPath<Integer> usingNumber = createNumber("usingNumber", Integer.class);

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

