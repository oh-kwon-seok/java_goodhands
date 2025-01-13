package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStockInout is a Querydsl query type for StockInout
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStockInout extends EntityPathBase<StockInout> {

    private static final long serialVersionUID = -1817486683L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStockInout stockInout = new QStockInout("stockInout");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath doc_type = createString("doc_type");

    public final StringPath status = createString("status");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final QUser user;

    public QStockInout(String variable) {
        this(StockInout.class, forVariable(variable), INITS);
    }

    public QStockInout(Path<? extends StockInout> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStockInout(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStockInout(PathMetadata metadata, PathInits inits) {
        this(StockInout.class, metadata, inits);
    }

    public QStockInout(Class<? extends StockInout> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

