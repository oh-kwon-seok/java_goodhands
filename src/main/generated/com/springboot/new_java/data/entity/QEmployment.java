package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmployment is a Querydsl query type for Employment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployment extends EntityPathBase<Employment> {

    private static final long serialVersionUID = 2000196606L;

    public static final QEmployment employment = new QEmployment("employment");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath name = createString("name");

    public final StringPath name2 = createString("name2");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final BooleanPath used = createBoolean("used");

    public QEmployment(String variable) {
        super(Employment.class, forVariable(variable));
    }

    public QEmployment(Path<? extends Employment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmployment(PathMetadata metadata) {
        super(Employment.class, metadata);
    }

}

