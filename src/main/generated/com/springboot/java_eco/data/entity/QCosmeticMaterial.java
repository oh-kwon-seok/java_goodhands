package com.springboot.java_eco.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCosmeticMaterial is a Querydsl query type for CosmeticMaterial
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCosmeticMaterial extends EntityPathBase<CosmeticMaterial> {

    private static final long serialVersionUID = -699353611L;

    public static final QCosmeticMaterial cosmeticMaterial = new QCosmeticMaterial("cosmeticMaterial");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath cas_no = createString("cas_no");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath ingr_eng_name = createString("ingr_eng_name");

    public final StringPath ingr_kor_name = createString("ingr_kor_name");

    public final StringPath ingr_synonym = createString("ingr_synonym");

    public final StringPath origin_major_kor_name = createString("origin_major_kor_name");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public QCosmeticMaterial(String variable) {
        super(CosmeticMaterial.class, forVariable(variable));
    }

    public QCosmeticMaterial(Path<? extends CosmeticMaterial> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCosmeticMaterial(PathMetadata metadata) {
        super(CosmeticMaterial.class, metadata);
    }

}

