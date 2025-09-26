package com.springboot.new_java.data.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsRepository extends JpaRepository<Sns,Long> {
    Optional<Sns> findByEmail(String nickname);

}
