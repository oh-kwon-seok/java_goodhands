package com.springboot.new_java.data.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSns is a Querydsl query type for Sns
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSns extends EntityPathBase<Sns> {

    private static final long serialVersionUID = 1472388518L;

    public static final QSns sns = new QSns("sns");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final EnumPath<com.springboot.new_java.authentication.domain.oauth.OAuthProvider> oAuthProvider = createEnum("oAuthProvider", com.springboot.new_java.authentication.domain.oauth.OAuthProvider.class);

    public QSns(String variable) {
        super(Sns.class, forVariable(variable));
    }

    public QSns(Path<? extends Sns> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSns(PathMetadata metadata) {
        super(Sns.class, metadata);
    }

}

