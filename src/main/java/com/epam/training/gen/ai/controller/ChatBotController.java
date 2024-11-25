package com.epam.training.gen.ai.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.training.gen.ai.model.AiResponse;
import com.epam.training.gen.ai.model.UserRequest;
import com.epam.training.gen.ai.service.KernelService;
import com.epam.training.gen.ai.service.OpenAIService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class ChatBotController {

  private final OpenAIService interactionService;
  private final KernelService computationService;

  /**
   * Handle requests for semantic analysis.
   *
   * @param request User input request object.
   * @return AiResponse Response object encapsulating result.
   */
  @PostMapping("/analyze")
  public AiResponse processSemanticAnalysis(@RequestBody final UserRequest request) {
    final String response = computationService.processInputHistorically(request.getInput(), request.getTemperature());
    return new AiResponse(List.of(response));
  }

  /**
   * Direct API call to OpenAI for chat completions.
   *
   * @param request User input request object.
   * @return AiResponse Response object encapsulating obtained messages.
   */
  @PostMapping("/chat")
  public AiResponse generateChatResponse(@RequestBody final UserRequest request) {
    final List<String> responses = interactionService.retrieveChatCompletions(request);
    return new AiResponse(responses);
  }
}
