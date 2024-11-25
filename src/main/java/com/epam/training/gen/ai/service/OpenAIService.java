package com.epam.training.gen.ai.service;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.epam.training.gen.ai.config.AppProperties;
import com.epam.training.gen.ai.model.UserRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAIService {

  private final AppProperties appProperties;
  private final OpenAIAsyncClient aiClient;

  /**
   * Retrieves chat completions from OpenAI based on user request.
   *
   * @param request UserRequest containing user input.
   * @return List<String> List of message responses.
   */
  public List<String> retrieveChatCompletions(final UserRequest request) {
    try {
      final List<String> responses = performCall(request);
      log.info("Retrieved Messages: {}", responses);
      return responses;
    } catch (Exception e) {
      log.error("Failed to retrieve chat completions: {}", e.getMessage(), e);
      return List.of("Failed to retrieve answer. Please try again later.");
    }
  }

  private List<String> performCall(final UserRequest request) {
    final ChatCompletions completions = aiClient.getChatCompletions(
        appProperties.getClientAzureopenaiDeploymentName(),
        new ChatCompletionsOptions(List.of(new ChatRequestUserMessage(request.input())))
      )
      .block();

    return completions.getChoices().stream()
      .map(choice -> choice.getMessage().getContent())
      .collect(Collectors.toList());
  }
}
