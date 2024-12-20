package com.epam.training.gen.ai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties
public class AppProperties {

  private String clientAzureopenaiKey;
  private String clientAzureopenaiEndpoint;
  private String clientAzureopenaiDeploymentName;
  private String clientEmbeddedModelId;
  private Integer aiRequestMaxTokens;
  private Double aiDefaultTemperature;
}
