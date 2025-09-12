package com.springboot.new_java.data.entity.disease;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiseaseCategory is a Querydsl query type for DiseaseCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiseaseCategory extends EntityPathBase<DiseaseCategory> {

    private static final long serialVersionUID = -1116714154L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiseaseCategory diseaseCategory = new QDiseaseCategory("diseaseCategory");

    public final com.springboot.new_java.data.entity.QBaseEntity _super = new com.springboot.new_java.data.entity.QBaseEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final StringPath name = createString("name");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final BooleanPath used = createBoolean("used");

    public final com.springboot.new_java.data.entity.QUser user;

    public QDiseaseCategory(String variable) {
        this(DiseaseCategory.class, forVariable(variable), INITS);
    }

    public QDiseaseCategory(Path<? extends DiseaseCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiseaseCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiseaseCategory(PathMetadata metadata, PathInits inits) {
        this(DiseaseCategory.class, metadata, inits);
    }

    public QDiseaseCategory(Class<? extends DiseaseCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.springboot.new_java.data.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

