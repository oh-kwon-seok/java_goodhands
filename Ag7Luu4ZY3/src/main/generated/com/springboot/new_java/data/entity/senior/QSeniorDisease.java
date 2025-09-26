package com.springboot.new_java.data.entity.senior;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeniorDisease is a Querydsl query type for SeniorDisease
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeniorDisease extends EntityPathBase<SeniorDisease> {

    private static final long serialVersionUID = -2014685376L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSeniorDisease seniorDisease = new QSeniorDisease("seniorDisease");

    public final StringPath diagnosis_date = createString("diagnosis_date");

    public final com.springboot.new_java.data.entity.disease.QDisease disease;

    public final StringPath note = createString("note");

    public final QSenior senior;

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    public final BooleanPath use_auto_checklist = createBoolean("use_auto_checklist");

    public QSeniorDisease(String variable) {
        this(SeniorDisease.class, forVariable(variable), INITS);
    }

    public QSeniorDisease(Path<? extends SeniorDisease> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSeniorDisease(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSeniorDisease(PathMetadata metadata, PathInits inits) {
        this(SeniorDisease.class, metadata, inits);
    }

    public QSeniorDisease(Class<? extends SeniorDisease> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.disease = inits.isInitialized("disease") ? new com.springboot.new_java.data.entity.disease.QDisease(forProperty("disease"), inits.get("disease")) : null;
        this.senior = inits.isInitialized("senior") ? new QSenior(forProperty("senior"), inits.get("senior")) : null;
    }

}

