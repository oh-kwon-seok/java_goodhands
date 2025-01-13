package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStockRecord is a Querydsl query type for StockRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStockRecord extends EntityPathBase<StockRecord> {

    private static final long serialVersionUID = -258524875L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStockRecord stockRecord = new QStockRecord("stockRecord");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final QFactory inFactory;

    public final QFactorySub inFactorySub;

    public final QItem item;

    public final StringPath lot = createString("lot");

    public final QFactory outFactory;

    public final QFactorySub outFactorySub;

    public final NumberPath<Double> qty = createNumber("qty", Double.class);

    public final StringPath reason = createString("reason");

    public final StringPath status = createString("status");

    public final StringPath type = createString("type");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public QStockRecord(String variable) {
        this(StockRecord.class, forVariable(variable), INITS);
    }

    public QStockRecord(Path<? extends StockRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStockRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStockRecord(PathMetadata metadata, PathInits inits) {
        this(StockRecord.class, metadata, inits);
    }

    public QStockRecord(Class<? extends StockRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.inFactory = inits.isInitialized("inFactory") ? new QFactory(forProperty("inFactory"), inits.get("inFactory")) : null;
        this.inFactorySub = inits.isInitialized("inFactorySub") ? new QFactorySub(forProperty("inFactorySub"), inits.get("inFactorySub")) : null;
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.outFactory = inits.isInitialized("outFactory") ? new QFactory(forProperty("outFactory"), inits.get("outFactory")) : null;
        this.outFactorySub = inits.isInitialized("outFactorySub") ? new QFactorySub(forProperty("outFactorySub"), inits.get("outFactorySub")) : null;
    }

}

