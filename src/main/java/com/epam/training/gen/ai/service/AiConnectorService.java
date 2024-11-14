package com.epam.training.gen.ai.service;


import org.springframework.stereotype.Service;

import com.epam.training.gen.ai.model.ResponseModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AiConnectorService {

  public ResponseModel getChatResponse(final String userPrompt) {
    log.info("Analyzing the prompt: " + userPrompt);
    final String botResponse = "Analyzing the prompt: " + userPrompt;
    return new ResponseModel(userPrompt, botResponse);
  }
}
