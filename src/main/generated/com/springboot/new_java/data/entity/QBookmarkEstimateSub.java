package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookmarkEstimateSub is a Querydsl query type for BookmarkEstimateSub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookmarkEstimateSub extends EntityPathBase<BookmarkEstimateSub> {

    private static final long serialVersionUID = -934414064L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookmarkEstimateSub bookmarkEstimateSub = new QBookmarkEstimateSub("bookmarkEstimateSub");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBookmarkEstimate bookmarkEstimate;

    public final NumberPath<Integer> buy_price = createNumber("buy_price", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final QItem item;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Double> qty = createNumber("qty", Double.class);

    public final NumberPath<Integer> supply_price = createNumber("supply_price", Integer.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final StringPath unit = createString("unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> vat_price = createNumber("vat_price", Integer.class);

    public QBookmarkEstimateSub(String variable) {
        this(BookmarkEstimateSub.class, forVariable(variable), INITS);
    }

    public QBookmarkEstimateSub(Path<? extends BookmarkEstimateSub> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookmarkEstimateSub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookmarkEstimateSub(PathMetadata metadata, PathInits inits) {
        this(BookmarkEstimateSub.class, metadata, inits);
    }

    public QBookmarkEstimateSub(Class<? extends BookmarkEstimateSub> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bookmarkEstimate = inits.isInitialized("bookmarkEstimate") ? new QBookmarkEstimate(forProperty("bookmarkEstimate"), inits.get("bookmarkEstimate")) : null;
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
    }

}

