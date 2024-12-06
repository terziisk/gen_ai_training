package com.epam.training.gen.ai.model;

import java.util.Optional;

import javax.annotation.Nullable;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
public final class UserRequest {

  @NonNull
  @Size(min = 1, message = "Input must not be empty")
  private final String input;

  @Nullable
  @DecimalMin(value = "0.0", inclusive = true, message = "Value must be greater than or equal to 0.0")
  @DecimalMax(value = "2.0", inclusive = true, message = "Value must be less than or equal to 2.0")
  private final Double temperature;


  public Optional<Double> getTemperature() {
    return Optional.ofNullable(temperature);
  }


}
