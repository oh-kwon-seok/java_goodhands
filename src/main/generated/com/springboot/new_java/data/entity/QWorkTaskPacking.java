package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWorkTaskPacking is a Querydsl query type for WorkTaskPacking
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWorkTaskPacking extends EntityPathBase<WorkTaskPacking> {

    private static final long serialVersionUID = -1070191295L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWorkTaskPacking workTaskPacking = new QWorkTaskPacking("workTaskPacking");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBom bom;

    public final NumberPath<Integer> box_qty = createNumber("box_qty", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final NumberPath<Integer> etc_qty = createNumber("etc_qty", Integer.class);

    public final QFactory factory;

    public final QFactorySub factorySub;

    public final NumberPath<Integer> inbox_qty = createNumber("inbox_qty", Integer.class);

    public final StringPath lot = createString("lot");

    public final NumberPath<java.math.BigDecimal> totalEtcQty = createNumber("totalEtcQty", java.math.BigDecimal.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final QWorkTask workTask;

    public final QWorkTaskProduct workTaskProduct;

    public QWorkTaskPacking(String variable) {
        this(WorkTaskPacking.class, forVariable(variable), INITS);
    }

    public QWorkTaskPacking(Path<? extends WorkTaskPacking> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWorkTaskPacking(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWorkTaskPacking(PathMetadata metadata, PathInits inits) {
        this(WorkTaskPacking.class, metadata, inits);
    }

    public QWorkTaskPacking(Class<? extends WorkTaskPacking> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bom = inits.isInitialized("bom") ? new QBom(forProperty("bom"), inits.get("bom")) : null;
        this.factory = inits.isInitialized("factory") ? new QFactory(forProperty("factory"), inits.get("factory")) : null;
        this.factorySub = inits.isInitialized("factorySub") ? new QFactorySub(forProperty("factorySub"), inits.get("factorySub")) : null;
        this.workTask = inits.isInitialized("workTask") ? new QWorkTask(forProperty("workTask"), inits.get("workTask")) : null;
        this.workTaskProduct = inits.isInitialized("workTaskProduct") ? new QWorkTaskProduct(forProperty("workTaskProduct"), inits.get("workTaskProduct")) : null;
    }

}

