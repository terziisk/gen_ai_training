package com.epam.training.gen.ai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.training.gen.ai.model.SearchEmbeddingResponse;
import com.epam.training.gen.ai.repository.EmbeddingRepository;
import com.epam.training.gen.ai.repository.entity.EmbeddingEntity;
import com.microsoft.semantickernel.aiservices.openai.textembedding.OpenAITextEmbeddingGenerationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmbeddingService {

  private final EmbeddingRepository repository;
  private final OpenAITextEmbeddingGenerationService embeddingGeneration;

  public List<Float> generateEmbedding(final String text) {
    final List<Float> vector = this.getEmbeddingFromText(text);
    log.info("Generated embedding vector for text {}: {} ", text, vector);
    return vector;
  }

  private List<Float> getEmbeddingFromText(final String text) {
    return embeddingGeneration.generateEmbeddingAsync(text)
      .map(response -> response.getVector())
      .block();
  }

  public EmbeddingEntity saveEmbedding(final String text) {
    final List<Float> embedding = generateEmbedding(text);
    final EmbeddingEntity newEmbedding = EmbeddingEntity.builder()
      .text(text)
      .embedding(toPrimitiveFloatArray(embedding))
      .build();
    return repository.save(newEmbedding);
  }

  public SearchEmbeddingResponse findClosestEmbeddings(final String input) {
    final List<Float> inputEmbedding = generateEmbedding(input);
    final List<EmbeddingEntity> closestByVector = repository.findClosestByVector(inputEmbedding);
    final List<String> texts = closestByVector.stream().map(EmbeddingEntity::getText).toList();
    return SearchEmbeddingResponse.builder()
      .texts(texts)
      .build();
  }

  private float[] toPrimitiveFloatArray(final List<Float> floatList) {
    final float[] primitiveArray = new float[floatList.size()];

    for (int i = 0; i < floatList.size(); i++) {
      primitiveArray[i] = floatList.get(i);
    }
    return primitiveArray;
  }

}
