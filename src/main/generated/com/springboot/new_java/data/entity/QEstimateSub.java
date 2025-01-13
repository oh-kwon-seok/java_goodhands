package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEstimateSub is a Querydsl query type for EstimateSub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEstimateSub extends EntityPathBase<EstimateSub> {

    private static final long serialVersionUID = -1331543514L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEstimateSub estimateSub = new QEstimateSub("estimateSub");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> buy_price = createNumber("buy_price", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final QEstimate estimate;

    public final QItem item;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Double> qty = createNumber("qty", Double.class);

    public final NumberPath<Integer> supply_price = createNumber("supply_price", Integer.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> vat_price = createNumber("vat_price", Integer.class);

    public QEstimateSub(String variable) {
        this(EstimateSub.class, forVariable(variable), INITS);
    }

    public QEstimateSub(Path<? extends EstimateSub> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEstimateSub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEstimateSub(PathMetadata metadata, PathInits inits) {
        this(EstimateSub.class, metadata, inits);
    }

    public QEstimateSub(Class<? extends EstimateSub> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.estimate = inits.isInitialized("estimate") ? new QEstimate(forProperty("estimate"), inits.get("estimate")) : null;
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
    }

}

