package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProcessQc is a Querydsl query type for ProcessQc
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProcessQc extends EntityPathBase<ProcessQc> {

    private static final long serialVersionUID = -897118577L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProcessQc processQc = new QProcessQc("processQc");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final StringPath name = createString("name");

    public final QProcess process;

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public QProcessQc(String variable) {
        this(ProcessQc.class, forVariable(variable), INITS);
    }

    public QProcessQc(Path<? extends ProcessQc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProcessQc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProcessQc(PathMetadata metadata, PathInits inits) {
        this(ProcessQc.class, metadata, inits);
    }

    public QProcessQc(Class<? extends ProcessQc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.process = inits.isInitialized("process") ? new QProcess(forProperty("process"), inits.get("process")) : null;
    }

}

