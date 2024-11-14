package com.epam.training.gen.ai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.training.gen.ai.model.ResponseModel;
import com.epam.training.gen.ai.service.AiConnectorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatBotController {

  private final AiConnectorService service;

  @GetMapping("/chat-response")
  public ResponseModel getChatResponse(@RequestParam(name = "prompt") final String userPrompt) {
    return service.getChatResponse(userPrompt);
  }
}
