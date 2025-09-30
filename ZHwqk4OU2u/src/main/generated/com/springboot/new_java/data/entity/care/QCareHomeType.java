package com.springboot.new_java.data.entity.care;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCareHomeType is a Querydsl query type for CareHomeType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCareHomeType extends EntityPathBase<CareHomeType> {

    private static final long serialVersionUID = 476245653L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCareHomeType careHomeType = new QCareHomeType("careHomeType");

    public final com.springboot.new_java.data.entity.QBaseEntity _super = new com.springboot.new_java.data.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath name = createString("name");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final BooleanPath used = createBoolean("used");

    public final com.springboot.new_java.data.entity.QUser user;

    public QCareHomeType(String variable) {
        this(CareHomeType.class, forVariable(variable), INITS);
    }

    public QCareHomeType(Path<? extends CareHomeType> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCareHomeType(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCareHomeType(PathMetadata metadata, PathInits inits) {
        this(CareHomeType.class, metadata, inits);
    }

    public QCareHomeType(Class<? extends CareHomeType> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.springboot.new_java.data.entity.QUser(forProperty("user")) : null;
    }

}

