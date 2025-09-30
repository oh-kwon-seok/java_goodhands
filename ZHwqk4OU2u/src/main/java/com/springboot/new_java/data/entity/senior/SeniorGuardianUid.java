package com.springboot.new_java.data.entity.senior;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SeniorGuardianUid implements Serializable {

    @Column(name = "senior_uid")
    private Long seniorUid;

    @Column(name = "guardian_id")
    private String guardianId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeniorGuardianUid)) return false;
        SeniorGuardianUid that = (SeniorGuardianUid) o;
        return Objects.equals(seniorUid, that.seniorUid) &&
                Objects.equals(guardianId, that.guardianId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seniorUid, guardianId);
    }
}
