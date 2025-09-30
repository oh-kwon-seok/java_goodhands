package com.springboot.new_java.data.entity.senior;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSenior is a Querydsl query type for Senior
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSenior extends EntityPathBase<Senior> {

    private static final long serialVersionUID = 2103331484L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSenior senior = new QSenior("senior");

    public final com.springboot.new_java.data.entity.QBaseEntity _super = new com.springboot.new_java.data.entity.QBaseEntity(this);

    public final StringPath birth_date = createString("birth_date");

    public final StringPath care_schedule_protocol = createString("care_schedule_protocol");

    public final com.springboot.new_java.data.entity.QUser caregiver;

    public final com.springboot.new_java.data.entity.care.QCareHome careHome;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath name = createString("name");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final BooleanPath use_care_schedule = createBoolean("use_care_schedule");

    public final BooleanPath used = createBoolean("used");

    public final com.springboot.new_java.data.entity.QUser user;

    public QSenior(String variable) {
        this(Senior.class, forVariable(variable), INITS);
    }

    public QSenior(Path<? extends Senior> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSenior(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSenior(PathMetadata metadata, PathInits inits) {
        this(Senior.class, metadata, inits);
    }

    public QSenior(Class<? extends Senior> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.caregiver = inits.isInitialized("caregiver") ? new com.springboot.new_java.data.entity.QUser(forProperty("caregiver")) : null;
        this.careHome = inits.isInitialized("careHome") ? new com.springboot.new_java.data.entity.care.QCareHome(forProperty("careHome"), inits.get("careHome")) : null;
        this.user = inits.isInitialized("user") ? new com.springboot.new_java.data.entity.QUser(forProperty("user")) : null;
    }

}

