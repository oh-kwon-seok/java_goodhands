package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkTaskProduct is a Querydsl query type for WorkTaskProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkTaskProduct extends EntityPathBase<WorkTaskProduct> {

    private static final long serialVersionUID = -572610809L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkTaskProduct workTaskProduct = new QWorkTaskProduct("workTaskProduct");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBom bom;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath lot = createString("lot");

    public final NumberPath<Double> product_qty = createNumber("product_qty", Double.class);

    public final StringPath status = createString("status");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final QWorkTask workTask;

    public QWorkTaskProduct(String variable) {
        this(WorkTaskProduct.class, forVariable(variable), INITS);
    }

    public QWorkTaskProduct(Path<? extends WorkTaskProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkTaskProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkTaskProduct(PathMetadata metadata, PathInits inits) {
        this(WorkTaskProduct.class, metadata, inits);
    }

    public QWorkTaskProduct(Class<? extends WorkTaskProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bom = inits.isInitialized("bom") ? new QBom(forProperty("bom"), inits.get("bom")) : null;
        this.workTask = inits.isInitialized("workTask") ? new QWorkTask(forProperty("workTask"), inits.get("workTask")) : null;
    }

}

