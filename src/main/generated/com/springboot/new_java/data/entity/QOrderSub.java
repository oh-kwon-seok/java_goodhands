package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderSub is a Querydsl query type for OrderSub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderSub extends EntityPathBase<OrderSub> {

    private static final long serialVersionUID = -1945919324L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderSub orderSub = new QOrderSub("orderSub");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> buy_price = createNumber("buy_price", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final QItem item;

    public final QOrder order;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Double> qty = createNumber("qty", Double.class);

    public final NumberPath<Integer> supply_price = createNumber("supply_price", Integer.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> vat_price = createNumber("vat_price", Integer.class);

    public QOrderSub(String variable) {
        this(OrderSub.class, forVariable(variable), INITS);
    }

    public QOrderSub(Path<? extends OrderSub> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderSub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderSub(PathMetadata metadata, PathInits inits) {
        this(OrderSub.class, metadata, inits);
    }

    public QOrderSub(Class<? extends OrderSub> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
        this.order = inits.isInitialized("order") ? new QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

