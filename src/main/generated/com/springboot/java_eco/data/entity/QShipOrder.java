package com.springboot.java_eco.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShipOrder is a Querydsl query type for ShipOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShipOrder extends EntityPathBase<ShipOrder> {

    private static final long serialVersionUID = -2130851213L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShipOrder shipOrder = new QShipOrder("shipOrder");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath code = createString("code");

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final QCompany customer;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final StringPath name = createString("name");

    public final QOrder order;

    public final NumberPath<Integer> order_count = createNumber("order_count", Integer.class);

    public final StringPath product_spec = createString("product_spec");

    public final StringPath ship_date = createString("ship_date");

    public final StringPath ship_place = createString("ship_place");

    public final StringPath status = createString("status");

    public final NumberPath<java.math.BigDecimal> totalBuyPrice = createNumber("totalBuyPrice", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> totalSupplyPrice = createNumber("totalSupplyPrice", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> totalVatPrice = createNumber("totalVatPrice", java.math.BigDecimal.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public final QUser user;

    public QShipOrder(String variable) {
        this(ShipOrder.class, forVariable(variable), INITS);
    }

    public QShipOrder(Path<? extends ShipOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShipOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShipOrder(PathMetadata metadata, PathInits inits) {
        this(ShipOrder.class, metadata, inits);
    }

    public QShipOrder(Class<? extends ShipOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.customer = inits.isInitialized("customer") ? new QCompany(forProperty("customer")) : null;
        this.order = inits.isInitialized("order") ? new QOrder(forProperty("order"), inits.get("order")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

