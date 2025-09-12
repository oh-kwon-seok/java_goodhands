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

    public final DatePath<java.time.LocalDate> diagnosisDate = createDate("diagnosisDate", java.time.LocalDate.class);

    public final com.springboot.new_java.data.entity.disease.QDisease disease;

    public final QSeniorDiseaseUid id;

    public final BooleanPath is_auto_checklist = createBoolean("is_auto_checklist");

    public final StringPath note = createString("note");

    public final QSenior senior;

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
        this.id = inits.isInitialized("id") ? new QSeniorDiseaseUid(forProperty("id")) : null;
        this.senior = inits.isInitialized("senior") ? new QSenior(forProperty("senior"), inits.get("senior")) : null;
    }

}

