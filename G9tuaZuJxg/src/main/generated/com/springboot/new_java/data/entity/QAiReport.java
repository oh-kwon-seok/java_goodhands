package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAiReport is a Querydsl query type for AiReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAiReport extends EntityPathBase<AiReport> {

    private static final long serialVersionUID = -488416242L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAiReport aiReport = new QAiReport("aiReport");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath ai_report = createString("ai_report");

    public final com.springboot.new_java.data.entity.care.QCareLog careLog;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public QAiReport(String variable) {
        this(AiReport.class, forVariable(variable), INITS);
    }

    public QAiReport(Path<? extends AiReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAiReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAiReport(PathMetadata metadata, PathInits inits) {
        this(AiReport.class, metadata, inits);
    }

    public QAiReport(Class<? extends AiReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.careLog = inits.isInitialized("careLog") ? new com.springboot.new_java.data.entity.care.QCareLog(forProperty("careLog"), inits.get("careLog")) : null;
    }

}

