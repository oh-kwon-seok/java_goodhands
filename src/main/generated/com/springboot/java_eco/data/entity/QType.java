package com.springboot.java_eco.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QType is a Querydsl query type for Type
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QType extends EntityPathBase<Type> {

    private static final long serialVersionUID = -218772839L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QType type = new QType("type1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath name = createString("name");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public QType(String variable) {
        this(Type.class, forVariable(variable), INITS);
    }

    public QType(Path<? extends Type> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QType(PathMetadata metadata, PathInits inits) {
        this(Type.class, metadata, inits);
    }

    public QType(Class<? extends Type> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

