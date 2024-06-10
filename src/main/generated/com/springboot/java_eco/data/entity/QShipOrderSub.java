package com.springboot.java_eco.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShipOrderSub is a Querydsl query type for ShipOrderSub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShipOrderSub extends EntityPathBase<ShipOrderSub> {

    private static final long serialVersionUID = -571768115L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShipOrderSub shipOrderSub = new QShipOrderSub("shipOrderSub");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> buy_price = createNumber("buy_price", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final QItem item;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Double> qty = createNumber("qty", Double.class);

    public final QShipOrder shipOrder;

    public final QStock stock;

    public final NumberPath<Integer> supply_price = createNumber("supply_price", Integer.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> vat_price = createNumber("vat_price", Integer.class);

    public QShipOrderSub(String variable) {
        this(ShipOrderSub.class, forVariable(variable), INITS);
    }

    public QShipOrderSub(Path<? extends ShipOrderSub> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShipOrderSub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShipOrderSub(PathMetadata metadata, PathInits inits) {
        this(ShipOrderSub.class, metadata, inits);
    }

    public QShipOrderSub(Class<? extends ShipOrderSub> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.shipOrder = inits.isInitialized("shipOrder") ? new QShipOrder(forProperty("shipOrder"), inits.get("shipOrder")) : null;
        this.stock = inits.isInitialized("stock") ? new QStock(forProperty("stock"), inits.get("stock")) : null;
    }

}

