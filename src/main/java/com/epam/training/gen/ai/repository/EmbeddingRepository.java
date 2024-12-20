package com.epam.training.gen.ai.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.postgresql.util.PGobject;
import org.springframework.stereotype.Repository;

import com.epam.training.gen.ai.repository.entity.EmbeddingEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EmbeddingRepository {


  @PersistenceContext
  private final EntityManager entityManager;
  private final EmbeddingJpaRepository jpaRepository;

  public EmbeddingEntity save(final EmbeddingEntity newEmbedding) {
    return jpaRepository.save(newEmbedding);
  }

  public List<EmbeddingEntity> findClosestByVector(final List<Float> inputEmbedding) {
    final String stringEmbedding = convertFloatToString(inputEmbedding);
    final String sqlQuery =
      "SELECT id, text, embedding FROM embeddings ORDER BY embedding <-> '" + stringEmbedding + "'  LIMIT 5";
    final List<Object[]> rows = entityManager.createNativeQuery(sqlQuery).getResultList();

    final List<EmbeddingEntity> resultList = new ArrayList<>();
    for (Object[] row : rows) {
      Long id = ((Number) row[0]).longValue();
      String text = (String) row[1];
      // manual conversion of datatype comes here
      PGobject pgObject = (PGobject) row[2];
      String vectorString = pgObject.getValue();
      // Assuming function parseVectorString handles parsing '(1.0, 2.0, 3.0)' -> [1.0, 2.0, 3.0]
      float[] embedding = parseVectorString(vectorString);

      resultList.add(new EmbeddingEntity(id, text, embedding));
    }
    return resultList;
  }

  private String convertFloatToString(final List<Float> inputEmbedding) {
    return inputEmbedding.stream()
      .map(Object::toString)
      .collect(Collectors.joining(",", "[", "]"));
  }

  // (current code showing how to parse the textual representation of a vector)
  private float[] parseVectorString(String vectorString) {
    vectorString = vectorString.replaceAll("[\\[\\]()]", "");
    String[] elements = vectorString.split(",");
    float[] vector = new float[elements.length];

    for (int i = 0; i < elements.length; i++) {
      vector[i] = Float.parseFloat(elements[i].trim());
    }
    return vector;
  }

}
