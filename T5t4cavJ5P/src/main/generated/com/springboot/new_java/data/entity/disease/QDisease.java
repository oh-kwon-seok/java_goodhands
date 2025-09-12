package com.springboot.new_java.data.entity.disease;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDisease is a Querydsl query type for Disease
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDisease extends EntityPathBase<Disease> {

    private static final long serialVersionUID = 966582840L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDisease disease = new QDisease("disease");

    public final com.springboot.new_java.data.entity.QBaseEntity _super = new com.springboot.new_java.data.entity.QBaseEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final StringPath disease_checklist = createString("disease_checklist");

    public final QDiseaseCategory diseaseCategory;

    public final BooleanPath is_chronic = createBoolean("is_chronic");

    public final BooleanPath is_disease_checklist = createBoolean("is_disease_checklist");

    public final StringPath name = createString("name");

    public final ListPath<com.springboot.new_java.data.entity.senior.SeniorDisease, com.springboot.new_java.data.entity.senior.QSeniorDisease> seniorDiseases = this.<com.springboot.new_java.data.entity.senior.SeniorDisease, com.springboot.new_java.data.entity.senior.QSeniorDisease>createList("seniorDiseases", com.springboot.new_java.data.entity.senior.SeniorDisease.class, com.springboot.new_java.data.entity.senior.QSeniorDisease.class, PathInits.DIRECT2);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final BooleanPath used = createBoolean("used");

    public final com.springboot.new_java.data.entity.QUser user;

    public QDisease(String variable) {
        this(Disease.class, forVariable(variable), INITS);
    }

    public QDisease(Path<? extends Disease> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDisease(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDisease(PathMetadata metadata, PathInits inits) {
        this(Disease.class, metadata, inits);
    }

    public QDisease(Class<? extends Disease> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.diseaseCategory = inits.isInitialized("diseaseCategory") ? new QDiseaseCategory(forProperty("diseaseCategory"), inits.get("diseaseCategory")) : null;
        this.user = inits.isInitialized("user") ? new com.springboot.new_java.data.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

