package com.mamahealth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamahealth.entity.Mother;
import com.mamahealth.entity.User;

public interface MotherRepository extends JpaRepository<Mother, Long> {

    Optional<Mother> findByUser(User user);

}