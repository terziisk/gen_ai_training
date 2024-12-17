package com.epam.training.gen.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@Setter
public class UserRecord {
  private String recordType;
  private String content;

}
