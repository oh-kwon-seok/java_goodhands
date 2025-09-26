package com.springboot.new_java.data.entity.senior;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeniorGuardianUid is a Querydsl query type for SeniorGuardianUid
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSeniorGuardianUid extends BeanPath<SeniorGuardianUid> {

    private static final long serialVersionUID = -372719421L;

    public static final QSeniorGuardianUid seniorGuardianUid = new QSeniorGuardianUid("seniorGuardianUid");

    public final StringPath guardianId = createString("guardianId");

    public final NumberPath<Long> seniorUid = createNumber("seniorUid", Long.class);

    public QSeniorGuardianUid(String variable) {
        super(SeniorGuardianUid.class, forVariable(variable));
    }

    public QSeniorGuardianUid(Path<? extends SeniorGuardianUid> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeniorGuardianUid(PathMetadata metadata) {
        super(SeniorGuardianUid.class, metadata);
    }

}

