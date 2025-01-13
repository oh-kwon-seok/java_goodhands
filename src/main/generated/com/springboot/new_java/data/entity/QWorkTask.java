package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkTask is a Querydsl query type for WorkTask
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkTask extends EntityPathBase<WorkTask> {

    private static final long serialVersionUID = 1149586632L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkTask workTask = new QWorkTask("workTask");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBom bom;

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final NumberPath<Double> fail_qty = createNumber("fail_qty", Double.class);

    public final NumberPath<Long> material_order = createNumber("material_order", Long.class);

    public final NumberPath<Long> measure_order = createNumber("measure_order", Long.class);

    public final NumberPath<Long> packing_order = createNumber("packing_order", Long.class);

    public final NumberPath<Long> production_order = createNumber("production_order", Long.class);

    public final StringPath status = createString("status");

    public final NumberPath<Double> success_qty = createNumber("success_qty", Double.class);

    public final NumberPath<Double> task_qty = createNumber("task_qty", Double.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final QUser user;

    public final StringPath work_end_date = createString("work_end_date");

    public final StringPath work_start_date = createString("work_start_date");

    public final QWorkPlan workPlan;

    public QWorkTask(String variable) {
        this(WorkTask.class, forVariable(variable), INITS);
    }

    public QWorkTask(Path<? extends WorkTask> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkTask(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkTask(PathMetadata metadata, PathInits inits) {
        this(WorkTask.class, metadata, inits);
    }

    public QWorkTask(Class<? extends WorkTask> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bom = inits.isInitialized("bom") ? new QBom(forProperty("bom"), inits.get("bom")) : null;
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
        this.workPlan = inits.isInitialized("workPlan") ? new QWorkPlan(forProperty("workPlan"), inits.get("workPlan")) : null;
    }

}

