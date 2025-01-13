package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStockControl is a Querydsl query type for StockControl
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStockControl extends EntityPathBase<StockControl> {

    private static final long serialVersionUID = 444609785L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStockControl stockControl = new QStockControl("stockControl");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Double> after_qty = createNumber("after_qty", Double.class);

    public final QFactory afterFactory;

    public final QFactorySub afterFactorySub;

    public final QCompany company;

    public final StringPath control_reason = createString("control_reason");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final QItem item;

    public final StringPath lot = createString("lot");

    public final NumberPath<Double> prev_qty = createNumber("prev_qty", Double.class);

    public final QFactory prevFactory;

    public final QFactorySub prevFactorySub;

    public final StringPath status = createString("status");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final QUser user;

    public QStockControl(String variable) {
        this(StockControl.class, forVariable(variable), INITS);
    }

    public QStockControl(Path<? extends StockControl> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStockControl(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStockControl(PathMetadata metadata, PathInits inits) {
        this(StockControl.class, metadata, inits);
    }

    public QStockControl(Class<? extends StockControl> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.afterFactory = inits.isInitialized("afterFactory") ? new QFactory(forProperty("afterFactory"), inits.get("afterFactory")) : null;
        this.afterFactorySub = inits.isInitialized("afterFactorySub") ? new QFactorySub(forProperty("afterFactorySub"), inits.get("afterFactorySub")) : null;
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.prevFactory = inits.isInitialized("prevFactory") ? new QFactory(forProperty("prevFactory"), inits.get("prevFactory")) : null;
        this.prevFactorySub = inits.isInitialized("prevFactorySub") ? new QFactorySub(forProperty("prevFactorySub"), inits.get("prevFactorySub")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

