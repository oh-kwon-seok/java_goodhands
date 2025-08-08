package com.springboot.new_java.data.entity.care;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCareSchedule is a Querydsl query type for CareSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCareSchedule extends EntityPathBase<CareSchedule> {

    private static final long serialVersionUID = 264507507L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCareSchedule careSchedule = new QCareSchedule("careSchedule");

    public final com.springboot.new_java.data.entity.QBaseEntity _super = new com.springboot.new_java.data.entity.QBaseEntity(this);

    public final StringPath care_real_date = createString("care_real_date");

    public final StringPath care_reserve_date = createString("care_reserve_date");

    public final com.springboot.new_java.data.entity.QUser caregiver;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath file_path = createString("file_path");

    public final com.springboot.new_java.data.entity.senior.QSenior senior;

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final com.springboot.new_java.data.entity.QUser user;

    public QCareSchedule(String variable) {
        this(CareSchedule.class, forVariable(variable), INITS);
    }

    public QCareSchedule(Path<? extends CareSchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCareSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCareSchedule(PathMetadata metadata, PathInits inits) {
        this(CareSchedule.class, metadata, inits);
    }

    public QCareSchedule(Class<? extends CareSchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.caregiver = inits.isInitialized("caregiver") ? new com.springboot.new_java.data.entity.QUser(forProperty("caregiver"), inits.get("caregiver")) : null;
        this.senior = inits.isInitialized("senior") ? new com.springboot.new_java.data.entity.senior.QSenior(forProperty("senior"), inits.get("senior")) : null;
        this.user = inits.isInitialized("user") ? new com.springboot.new_java.data.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

