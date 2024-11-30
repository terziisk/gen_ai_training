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
import com.epam.training.gen.ai.model.UserRequest;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAIService {

  private final AppProperties appProperties;
  private final ChatHistory history;
  private final OpenAIAsyncClient aiClient;

  /**
   * Retrieves chat completions from OpenAI based on user request.
   * @param request UserRequest containing user input.
   * @return List<String> List of message responses.
   */
  public List<String> retrieveChatCompletions(final UserRequest request) {
    history.addUserMessage(request.getInput());
    final List<ChatRequestMessage> messages = convertToChatRequestMessages(history.getMessages());

    final ChatCompletionsOptions options = new ChatCompletionsOptions(messages);
    options.setTemperature(request.getTemperature().orElse(appProperties.getAiDefaultTemperature()));

    // Retrieve completions using the AI client
    final ChatCompletions completions = aiClient.getChatCompletions(
        appProperties.getClientAzureopenaiDeploymentName(), options)
      .block();

    final List<String> responses = completions.getChoices().stream()
      .map(choice -> choice.getMessage().getContent())
      .toList();

    responses.forEach(response -> history.addAssistantMessage(response));

    responses.forEach(response -> log.info("Chat response: {}", response));
    return responses;
  }

  private List<ChatRequestMessage> convertToChatRequestMessages(List<ChatMessageContent<?>> contents) {
    return contents.stream()
      .map(ChatMessageContent::getContent)
      .map(ChatRequestUserMessage::new)
      .collect(toList());
  }
}
