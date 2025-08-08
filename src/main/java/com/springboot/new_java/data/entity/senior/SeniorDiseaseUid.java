package com.springboot.new_java.data.entity.senior;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SeniorDiseaseUid implements Serializable {

    @Column(name = "senior_uid")
    private Long seniorUid;

    @Column(name = "disease_uid")
    private Long diseaseUid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeniorDiseaseUid)) return false;
        SeniorDiseaseUid that = (SeniorDiseaseUid) o;
        return Objects.equals(seniorUid, that.seniorUid) &&
                Objects.equals(diseaseUid, that.diseaseUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seniorUid, diseaseUid);
    }
}
