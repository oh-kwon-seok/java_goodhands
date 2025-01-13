package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRestricMaterialCountry is a Querydsl query type for RestricMaterialCountry
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestricMaterialCountry extends EntityPathBase<RestricMaterialCountry> {

    private static final long serialVersionUID = -1895171671L;

    public static final QRestricMaterialCountry restricMaterialCountry = new QRestricMaterialCountry("restricMaterialCountry");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath country_name = createString("country_name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath ingr_code = createString("ingr_code");

    public final StringPath limit_cond = createString("limit_cond");

    public final StringPath notice_ingr_name = createString("notice_ingr_name");

    public final StringPath provis_atrcl = createString("provis_atrcl");

    public final StringPath regl_code = createString("regl_code");

    public final StringPath regulate_type = createString("regulate_type");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public QRestricMaterialCountry(String variable) {
        super(RestricMaterialCountry.class, forVariable(variable));
    }

    public QRestricMaterialCountry(Path<? extends RestricMaterialCountry> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestricMaterialCountry(PathMetadata metadata) {
        super(RestricMaterialCountry.class, metadata);
    }

}

