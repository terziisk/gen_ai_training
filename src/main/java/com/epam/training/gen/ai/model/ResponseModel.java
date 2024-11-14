package com.epam.training.gen.ai.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class ResponseModel {

  private final String input;
  private final String response;
}
