package com.springboot.java_eco.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSensor is a Querydsl query type for Sensor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSensor extends EntityPathBase<Sensor> {

    private static final long serialVersionUID = 165557081L;

    public static final QSensor sensor = new QSensor("sensor");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath code = createString("code");

    public final StringPath comment = createString("comment");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final NumberPath<Double> data = createNumber("data", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath type = createString("type");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public QSensor(String variable) {
        super(Sensor.class, forVariable(variable));
    }

    public QSensor(Path<? extends Sensor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSensor(PathMetadata metadata) {
        super(Sensor.class, metadata);
    }

}

