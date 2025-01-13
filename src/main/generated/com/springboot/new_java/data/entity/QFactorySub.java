package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFactorySub is a Querydsl query type for FactorySub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFactorySub extends EntityPathBase<FactorySub> {

    private static final long serialVersionUID = -659427096L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFactorySub factorySub = new QFactorySub("factorySub");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final QFactory factory;

    public final StringPath name = createString("name");

    public final StringPath status = createString("status");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public QFactorySub(String variable) {
        this(FactorySub.class, forVariable(variable), INITS);
    }

    public QFactorySub(Path<? extends FactorySub> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFactorySub(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFactorySub(PathMetadata metadata, PathInits inits) {
        this(FactorySub.class, metadata, inits);
    }

    public QFactorySub(Class<? extends FactorySub> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.factory = inits.isInitialized("factory") ? new QFactory(forProperty("factory"), inits.get("factory")) : null;
    }

}

