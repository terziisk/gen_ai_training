package com.epam.training.gen.ai.service;


import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatRequestMessage;
import com.azure.ai.openai.models.ChatRequestUserMessage;
import com.epam.training.gen.ai.config.AppProperties;
import com.epam.training.gen.ai.config.ModelHistoryManager;
import com.epam.training.gen.ai.model.UserRequest;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAIService {

  private final OpenAIAsyncClient aiClient;
  private final AppProperties appProperties;
  private final ModelHistoryManager modelHistoryManager;

  /**
   * Retrieves chat completions from OpenAI based on user request.
   *
   * @param request UserRequest containing user input.
   * @return List<String> List of message responses.
   */
  public List<String> retrieveChatCompletions(final UserRequest request) {
    final ChatHistory history = modelHistoryManager.getOrCreateHistory(
      appProperties.getClientAzureopenaiDeploymentName());
    return getChatResponses(request, history, appProperties.getClientAzureopenaiDeploymentName());
  }

  public List<String> retrieveChat(final UserRequest request, final String modelId) {
    final ChatHistory history = modelHistoryManager.getOrCreateHistory(modelId);
    return getChatResponses(request, history, modelId);
  }

  private List<String> getChatResponses(UserRequest request, ChatHistory history, String modelId) {
    try {
      history.addUserMessage(request.getInput());
      final List<ChatRequestMessage> messages = convertToChatRequestMessages(history.getMessages());

      final var options = new ChatCompletionsOptions(messages);
      options.setTemperature(request.getTemperature().orElse(appProperties.getAiDefaultTemperature()));

      final ChatCompletions completions = aiClient.getChatCompletions(modelId, options).block();

      final List<String> responses = completions.getChoices().stream()
        .map(choice -> choice.getMessage().getContent())
        .toList();

      responses.forEach(history::addAssistantMessage);
      responses.forEach(response -> log.info("Chat response: {}", response));

      return responses;
    } catch (Exception e) {
      log.error("Failed to retrieve chat completions: {}", e.getMessage(), e);
      return List.of("Failed to retrieve answer. Please try again later.");
    }
  }

  private List<ChatRequestMessage> convertToChatRequestMessages(List<ChatMessageContent<?>> contents) {
    return contents.stream()
      .map(ChatMessageContent::getContent)
      .map(ChatRequestUserMessage::new)
      .collect(toList());
  }
}
