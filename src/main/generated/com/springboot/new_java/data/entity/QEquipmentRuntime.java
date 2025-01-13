package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEquipmentRuntime is a Querydsl query type for EquipmentRuntime
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEquipmentRuntime extends EntityPathBase<EquipmentRuntime> {

    private static final long serialVersionUID = -1164254980L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEquipmentRuntime equipmentRuntime = new QEquipmentRuntime("equipmentRuntime");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final DateTimePath<java.time.LocalDateTime> end_time = createDateTime("end_time", java.time.LocalDateTime.class);

    public final QEquipment equipment;

    public final NumberPath<Integer> runtime_second = createNumber("runtime_second", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> start_time = createDateTime("start_time", java.time.LocalDateTime.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Long> used = createNumber("used", Long.class);

    public QEquipmentRuntime(String variable) {
        this(EquipmentRuntime.class, forVariable(variable), INITS);
    }

    public QEquipmentRuntime(Path<? extends EquipmentRuntime> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEquipmentRuntime(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEquipmentRuntime(PathMetadata metadata, PathInits inits) {
        this(EquipmentRuntime.class, metadata, inits);
    }

    public QEquipmentRuntime(Class<? extends EquipmentRuntime> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.equipment = inits.isInitialized("equipment") ? new QEquipment(forProperty("equipment"), inits.get("equipment")) : null;
    }

}

