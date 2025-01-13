package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployment is a Querydsl query type for Employment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployment extends EntityPathBase<Employment> {

    private static final long serialVersionUID = 2000196606L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployment employment = new QEmployment("employment");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath name = createString("name");

    public final StringPath name2 = createString("name2");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public QEmployment(String variable) {
        this(Employment.class, forVariable(variable), INITS);
    }

    public QEmployment(Path<? extends Employment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployment(PathMetadata metadata, PathInits inits) {
        this(Employment.class, metadata, inits);
    }

    public QEmployment(Class<? extends Employment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

