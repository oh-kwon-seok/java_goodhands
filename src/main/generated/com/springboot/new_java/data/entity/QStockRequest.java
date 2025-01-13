package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStockRequest is a Querydsl query type for StockRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStockRequest extends EntityPathBase<StockRequest> {

    private static final long serialVersionUID = 588759595L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStockRequest stockRequest = new QStockRequest("stockRequest");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final QItem item;

    public final NumberPath<Double> req_qty = createNumber("req_qty", Double.class);

    public final StringPath status = createString("status");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final QUser user;

    public final QWorkTask workTask;

    public QStockRequest(String variable) {
        this(StockRequest.class, forVariable(variable), INITS);
    }

    public QStockRequest(Path<? extends StockRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStockRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStockRequest(PathMetadata metadata, PathInits inits) {
        this(StockRequest.class, metadata, inits);
    }

    public QStockRequest(Class<? extends StockRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
        this.workTask = inits.isInitialized("workTask") ? new QWorkTask(forProperty("workTask"), inits.get("workTask")) : null;
    }

}

