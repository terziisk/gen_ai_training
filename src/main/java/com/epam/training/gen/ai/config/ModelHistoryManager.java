package com.epam.training.gen.ai.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;

@Component
public class ModelHistoryManager {

  private final ConcurrentMap<String, ChatHistory> modelHistoryMap = new ConcurrentHashMap<>();

  public ChatHistory getOrCreateHistory(String modelId) {
    return modelHistoryMap.computeIfAbsent(modelId, key -> new ChatHistory());
  }

}
