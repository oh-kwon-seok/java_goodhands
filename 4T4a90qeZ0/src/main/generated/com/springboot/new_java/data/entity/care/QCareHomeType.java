package com.springboot.new_java.data.entity.care;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCareHomeType is a Querydsl query type for CareHomeType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCareHomeType extends EntityPathBase<CareHomeType> {

    private static final long serialVersionUID = 476245653L;

    public static final QCareHomeType careHomeType = new QCareHomeType("careHomeType");

    public final com.springboot.new_java.data.entity.QBaseEntity _super = new com.springboot.new_java.data.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleted = _super.deleted;

    public final StringPath name = createString("name");

    public final NumberPath<Long> uid = createNumber("uid", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updated = _super.updated;

    public final BooleanPath used = createBoolean("used");

    public QCareHomeType(String variable) {
        super(CareHomeType.class, forVariable(variable));
    }

    public QCareHomeType(Path<? extends CareHomeType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCareHomeType(PathMetadata metadata) {
        super(CareHomeType.class, metadata);
    }

}

