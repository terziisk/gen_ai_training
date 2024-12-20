package com.epam.training.gen.ai.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class SearchEmbeddingResponse {

  private final List<String> texts;

}
