package com.epam.training.gen.ai.plugin;

import org.springframework.stereotype.Component;

import com.epam.training.gen.ai.model.SearchEmbeddingResponse;
import com.epam.training.gen.ai.service.EmbeddingService;
import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchPlugin {

  private final EmbeddingService embeddingService;

  @SneakyThrows
  @DefineKernelFunction(name = "findClosestEmbeddings",
    description = "Search for a information similar to the given query.")
  public String search(
    @KernelFunctionParameter(description = "Data on which to do action", name = "query") final String query
  ) {
    final SearchEmbeddingResponse response = embeddingService.findClosestEmbeddings(query);
    log.debug("response: {}", response);
    return response.getTexts().stream().findFirst().orElse("Not found");
  }
}
