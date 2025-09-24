package com.springboot.new_java.data.entity.senior;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
@Getter
@Setter
@Embeddable
public class SeniorDiseaseUid implements Serializable {

    @Column(name = "senior_uid")
    private Long senior_uid;

    @Column(name = "disease_uid")
    private Long disease_uid;


    public SeniorDiseaseUid() {}

    // 매개변수 생성자 추가
    public SeniorDiseaseUid(Long seniorUid, Long diseaseUid) {
        this.senior_uid = seniorUid;
        this.disease_uid = diseaseUid;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeniorDiseaseUid)) return false;
        SeniorDiseaseUid that = (SeniorDiseaseUid) o;
        return Objects.equals(senior_uid, that.senior_uid) &&
                Objects.equals(disease_uid, that.disease_uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senior_uid, disease_uid);
    }
}
