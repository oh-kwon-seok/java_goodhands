package com.springboot.new_java.data.entity.care;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCareHome is a Querydsl query type for CareHome
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCareHome extends EntityPathBase<CareHome> {

    private static final long serialVersionUID = -3161925L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCareHome careHome = new QCareHome("careHome");

    public final com.springboot.new_java.data.entity.QBaseEntity _super = new com.springboot.new_java.data.entity.QBaseEntity(this);

    public final QCareHomeType careHomeType;

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

    public QCareHome(String variable) {
        this(CareHome.class, forVariable(variable), INITS);
    }

    public QCareHome(Path<? extends CareHome> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCareHome(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCareHome(PathMetadata metadata, PathInits inits) {
        this(CareHome.class, metadata, inits);
    }

    public QCareHome(Class<? extends CareHome> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.careHomeType = inits.isInitialized("careHomeType") ? new QCareHomeType(forProperty("careHomeType"), inits.get("careHomeType")) : null;
        this.user = inits.isInitialized("user") ? new com.springboot.new_java.data.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

