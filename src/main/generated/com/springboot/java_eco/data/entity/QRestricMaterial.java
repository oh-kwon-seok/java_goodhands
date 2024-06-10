package com.springboot.java_eco.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRestricMaterial is a Querydsl query type for RestricMaterial
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestricMaterial extends EntityPathBase<RestricMaterial> {

    private static final long serialVersionUID = -1449742016L;

    public static final QRestricMaterial restricMaterial = new QRestricMaterial("restricMaterial");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath cas_no = createString("cas_no");

    public final StringPath country_name = createString("country_name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath ingr_eng_name = createString("ingr_eng_name");

    public final StringPath ingr_std_name = createString("ingr_std_name");

    public final StringPath ingr_synonym = createString("ingr_synonym");

    public final StringPath limit_cond = createString("limit_cond");

    public final StringPath notice_ingr_name = createString("notice_ingr_name");

    public final StringPath provis_atrcl = createString("provis_atrcl");

    public final StringPath regulate_type = createString("regulate_type");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public QRestricMaterial(String variable) {
        super(RestricMaterial.class, forVariable(variable));
    }

    public QRestricMaterial(Path<? extends RestricMaterial> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestricMaterial(PathMetadata metadata) {
        super(RestricMaterial.class, metadata);
    }

}

