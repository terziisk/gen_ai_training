package com.epam.training.gen.ai.repository.entity;


import static com.epam.training.gen.ai.config.AiConfig.EMBEDDING_DIMENSIONS;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "embeddings")
public class EmbeddingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;

  @Column(columnDefinition = "vector(" + EMBEDDING_DIMENSIONS + ")")
  private float[] embedding;

}
