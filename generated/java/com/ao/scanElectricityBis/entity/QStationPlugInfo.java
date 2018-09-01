package com.ao.scanElectricityBis.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStationPlugInfo is a Querydsl query type for StationPlugInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStationPlugInfo extends EntityPathBase<StationPlugInfo> {

    private static final long serialVersionUID = 288208904L;

    public static final QStationPlugInfo stationPlugInfo = new QStationPlugInfo("stationPlugInfo");

    public final QBaseBisEntity _super = new QBaseBisEntity(this);

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

    public QStationPlugInfo(String variable) {
        super(StationPlugInfo.class, forVariable(variable));
    }

    public QStationPlugInfo(Path<? extends StationPlugInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStationPlugInfo(PathMetadata metadata) {
        super(StationPlugInfo.class, metadata);
    }

}

