package com.springboot.java_eco.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStockInoutSub is a Querydsl query type for StockInoutSub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStockInoutSub extends EntityPathBase<StockInoutSub> {

    private static final long serialVersionUID = 137203246L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStockInoutSub stockInoutSub = new QStockInoutSub("stockInoutSub");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final QFactory factory;

    public final QFactorySub factorySub;

    public final QItem item;

    public final StringPath lot = createString("lot");

    public final NumberPath<Double> qty = createNumber("qty", Double.class);

    public final StringPath status = createString("status");

    public final QStockInout stockInout;

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public QStockInoutSub(String variable) {
        this(StockInoutSub.class, forVariable(variable), INITS);
    }

    public QStockInoutSub(Path<? extends StockInoutSub> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStockInoutSub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStockInoutSub(PathMetadata metadata, PathInits inits) {
        this(StockInoutSub.class, metadata, inits);
    }

    public QStockInoutSub(Class<? extends StockInoutSub> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.factory = inits.isInitialized("factory") ? new QFactory(forProperty("factory"), inits.get("factory")) : null;
        this.factorySub = inits.isInitialized("factorySub") ? new QFactorySub(forProperty("factorySub"), inits.get("factorySub")) : null;
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.stockInout = inits.isInitialized("stockInout") ? new QStockInout(forProperty("stockInout"), inits.get("stockInout")) : null;
    }

}

