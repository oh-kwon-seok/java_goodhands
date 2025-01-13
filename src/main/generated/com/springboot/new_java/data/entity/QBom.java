package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBom is a Querydsl query type for Bom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBom extends EntityPathBase<Bom> {

    private static final long serialVersionUID = 1472372206L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBom bom = new QBom("bom");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath code = createString("code");

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final QItem item;

    public final NumberPath<Long> main = createNumber("main", Long.class);

    public final NumberPath<Long> parent_uid = createNumber("parent_uid", Long.class);

    public final NumberPath<Double> qty = createNumber("qty", Double.class);

    public final NumberPath<Double> rate = createNumber("rate", Double.class);

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public QBom(String variable) {
        this(Bom.class, forVariable(variable), INITS);
    }

    public QBom(Path<? extends Bom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBom(PathMetadata metadata, PathInits inits) {
        this(Bom.class, metadata, inits);
    }

    public QBom(Class<? extends Bom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
    }

}

