package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkPlan is a Querydsl query type for WorkPlan
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkPlan extends EntityPathBase<WorkPlan> {

    private static final long serialVersionUID = 1149477484L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkPlan workPlan = new QWorkPlan("workPlan");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBom bom;

    public final StringPath code = createString("code");

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath endDate = createString("endDate");

    public final NumberPath<Double> qty = createNumber("qty", Double.class);

    public final StringPath startDate = createString("startDate");

    public final StringPath status = createString("status");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Long> used = createNumber("used", Long.class);

    public final QUser user;

    public QWorkPlan(String variable) {
        this(WorkPlan.class, forVariable(variable), INITS);
    }

    public QWorkPlan(Path<? extends WorkPlan> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkPlan(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkPlan(PathMetadata metadata, PathInits inits) {
        this(WorkPlan.class, metadata, inits);
    }

    public QWorkPlan(Class<? extends WorkPlan> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bom = inits.isInitialized("bom") ? new QBom(forProperty("bom"), inits.get("bom")) : null;
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

