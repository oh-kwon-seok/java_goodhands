package com.springboot.new_java.data.entity.care;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCareLog is a Querydsl query type for CareLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCareLog extends EntityPathBase<CareLog> {

    private static final long serialVersionUID = -1247024152L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCareLog careLog = new QCareLog("careLog");

    public final com.springboot.new_java.data.entity.QBaseEntity _super = new com.springboot.new_java.data.entity.QBaseEntity(this);

    public final StringPath caregiver_checklist = createString("caregiver_checklist");

    public final QCareSchedule careSchedule;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath end_time = createString("end_time");

    public final StringPath file_path = createString("file_path");

    public final StringPath state_time = createString("state_time");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final com.springboot.new_java.data.entity.QUser user;

    public QCareLog(String variable) {
        this(CareLog.class, forVariable(variable), INITS);
    }

    public QCareLog(Path<? extends CareLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCareLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCareLog(PathMetadata metadata, PathInits inits) {
        this(CareLog.class, metadata, inits);
    }

    public QCareLog(Class<? extends CareLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.careSchedule = inits.isInitialized("careSchedule") ? new QCareSchedule(forProperty("careSchedule"), inits.get("careSchedule")) : null;
        this.user = inits.isInitialized("user") ? new com.springboot.new_java.data.entity.QUser(forProperty("user")) : null;
    }

}

