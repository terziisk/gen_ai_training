package com.epam.training.gen.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.training.gen.ai.repository.entity.EmbeddingEntity;

public interface EmbeddingJpaRepository extends JpaRepository<EmbeddingEntity, Long> {

}
