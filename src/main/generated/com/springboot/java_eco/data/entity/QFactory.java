package com.springboot.java_eco.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFactory is a Querydsl query type for Factory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFactory extends EntityPathBase<Factory> {

    private static final long serialVersionUID = 2060010827L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFactory factory = new QFactory("factory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final StringPath factory_sub_array = createString("factory_sub_array");

    public final StringPath name = createString("name");

    public final StringPath status = createString("status");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public QFactory(String variable) {
        this(Factory.class, forVariable(variable), INITS);
    }

    public QFactory(Path<? extends Factory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFactory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFactory(PathMetadata metadata, PathInits inits) {
        this(Factory.class, metadata, inits);
    }

    public QFactory(Class<? extends Factory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

