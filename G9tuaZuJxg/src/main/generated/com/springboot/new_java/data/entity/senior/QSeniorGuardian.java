package com.springboot.new_java.data.entity.senior;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeniorGuardian is a Querydsl query type for SeniorGuardian
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeniorGuardian extends EntityPathBase<SeniorGuardian> {

    private static final long serialVersionUID = 164629581L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSeniorGuardian seniorGuardian = new QSeniorGuardian("seniorGuardian");

    public final com.springboot.new_java.data.entity.QUser guardian;

    public final QSeniorGuardianUid id;

    public final StringPath relationType = createString("relationType");

    public final QSenior senior;

    public QSeniorGuardian(String variable) {
        this(SeniorGuardian.class, forVariable(variable), INITS);
    }

    public QSeniorGuardian(Path<? extends SeniorGuardian> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSeniorGuardian(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSeniorGuardian(PathMetadata metadata, PathInits inits) {
        this(SeniorGuardian.class, metadata, inits);
    }

    public QSeniorGuardian(Class<? extends SeniorGuardian> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.guardian = inits.isInitialized("guardian") ? new com.springboot.new_java.data.entity.QUser(forProperty("guardian")) : null;
        this.id = inits.isInitialized("id") ? new QSeniorGuardianUid(forProperty("id")) : null;
        this.senior = inits.isInitialized("senior") ? new QSenior(forProperty("senior"), inits.get("senior")) : null;
    }

}

