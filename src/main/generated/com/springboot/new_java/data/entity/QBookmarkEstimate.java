package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBookmarkEstimate is a Querydsl query type for BookmarkEstimate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBookmarkEstimate extends EntityPathBase<BookmarkEstimate> {

    private static final long serialVersionUID = 1249489680L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBookmarkEstimate bookmarkEstimate = new QBookmarkEstimate("bookmarkEstimate");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final StringPath name = createString("name");

    public final StringPath product_spec = createString("product_spec");

    public final StringPath ship_place = createString("ship_place");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public final QUser user;

    public QBookmarkEstimate(String variable) {
        this(BookmarkEstimate.class, forVariable(variable), INITS);
    }

    public QBookmarkEstimate(Path<? extends BookmarkEstimate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBookmarkEstimate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBookmarkEstimate(PathMetadata metadata, PathInits inits) {
        this(BookmarkEstimate.class, metadata, inits);
    }

    public QBookmarkEstimate(Class<? extends BookmarkEstimate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

