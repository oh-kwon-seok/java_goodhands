package com.springboot.java_eco.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStockApproval is a Querydsl query type for StockApproval
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStockApproval extends EntityPathBase<StockApproval> {

    private static final long serialVersionUID = 880372474L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStockApproval stockApproval = new QStockApproval("stockApproval");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final QItem item;

    public final StringPath lot = createString("lot");

    public final NumberPath<Double> measure_qty = createNumber("measure_qty", Double.class);

    public final NumberPath<Double> out_qty = createNumber("out_qty", Double.class);

    public final NumberPath<Double> prev_qty = createNumber("prev_qty", Double.class);

    public final StringPath status = createString("status");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final QUser user;

    public final QWorkTask workTask;

    public QStockApproval(String variable) {
        this(StockApproval.class, forVariable(variable), INITS);
    }

    public QStockApproval(Path<? extends StockApproval> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStockApproval(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStockApproval(PathMetadata metadata, PathInits inits) {
        this(StockApproval.class, metadata, inits);
    }

    public QStockApproval(Class<? extends StockApproval> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
        this.workTask = inits.isInitialized("workTask") ? new QWorkTask(forProperty("workTask"), inits.get("workTask")) : null;
    }

}

