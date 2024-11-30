package com.epam.training.gen.ai.model;

import java.util.Optional;

import javax.annotation.Nullable;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
public final class UserRequest {

  private final @NonNull String input;
  @Nullable
  private final Double temperature;


  public Optional<Double> getTemperature() {
    return Optional.ofNullable(temperature);
  }


}
