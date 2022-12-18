package com.application.spring.repository;

import com.application.spring.model.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, UUID> {

    Producer findByNameLikeIgnoreCase(String name);
}
