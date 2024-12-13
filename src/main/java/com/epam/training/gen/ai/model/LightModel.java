package com.epam.training.gen.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@Setter
@ToString
public class LightModel {
  private int id;
  private String name;
  private boolean isOn;

}
