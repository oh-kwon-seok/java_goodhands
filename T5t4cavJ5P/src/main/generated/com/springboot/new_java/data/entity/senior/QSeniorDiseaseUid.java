package com.springboot.new_java.data.entity.senior;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeniorDiseaseUid is a Querydsl query type for SeniorDiseaseUid
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSeniorDiseaseUid extends BeanPath<SeniorDiseaseUid> {

    private static final long serialVersionUID = -1618957072L;

    public static final QSeniorDiseaseUid seniorDiseaseUid = new QSeniorDiseaseUid("seniorDiseaseUid");

    public final NumberPath<Long> diseaseUid = createNumber("diseaseUid", Long.class);

    public final NumberPath<Long> seniorUid = createNumber("seniorUid", Long.class);

    public QSeniorDiseaseUid(String variable) {
        super(SeniorDiseaseUid.class, forVariable(variable));
    }

    public QSeniorDiseaseUid(Path<? extends SeniorDiseaseUid> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeniorDiseaseUid(PathMetadata metadata) {
        super(SeniorDiseaseUid.class, metadata);
    }

}

