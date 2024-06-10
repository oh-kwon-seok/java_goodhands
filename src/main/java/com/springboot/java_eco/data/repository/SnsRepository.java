package com.springboot.java_eco.data.repository;
import com.springboot.java_eco.data.entity.Sns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsRepository extends JpaRepository<Sns,Long> {
    Optional<Sns> findByEmail(String nickname);

}
