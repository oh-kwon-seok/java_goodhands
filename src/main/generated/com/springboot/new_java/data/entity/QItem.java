package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = -1600888667L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath buy_type = createString("buy_type");

    public final StringPath classify_code = createString("classify_code");

    public final StringPath code = createString("code");

    public final QCompany company;

    public final StringPath component_code = createString("component_code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final StringPath currency_unit = createString("currency_unit");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath description = createString("description");

    public final StringPath file_path = createString("file_path");

    public final StringPath hs_code = createString("hs_code");

    public final StringPath image_url = createString("image_url");

    public final StringPath ingr_eng_name = createString("ingr_eng_name");

    public final StringPath ingr_kor_name = createString("ingr_kor_name");

    public final StringPath inout_type = createString("inout_type");

    public final StringPath inout_unit = createString("inout_unit");

    public final StringPath nts_code = createString("nts_code");

    public final StringPath simple_code = createString("simple_code");

    public final QType type;

    public final StringPath type_code = createString("type_code");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final NumberPath<Integer> used = createNumber("used", Integer.class);

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.type = inits.isInitialized("type") ? new QType(forProperty("type"), inits.get("type")) : null;
    }

}

