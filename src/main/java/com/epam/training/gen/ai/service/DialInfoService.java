package com.epam.training.gen.ai.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.epam.training.gen.ai.config.AppProperties;
import com.epam.training.gen.ai.model.GetDialModelsResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DialInfoService {
  private static final String API_URL = "https://ai-proxy.lab.epam.com/openai/deployments";
  private static final String HEADER_API_KEY = "Api-Key";

  private final RestTemplate restTemplate;
  private final AppProperties appProperties;

  public List<String> getModels() {
    final HttpHeaders headers = new HttpHeaders();
    headers.set(HEADER_API_KEY, appProperties.getClientAzureopenaiKey());

    final  HttpEntity<String> entity = new HttpEntity<>(headers);
    final  ResponseEntity<GetDialModelsResponse> response =
      restTemplate.exchange(API_URL, HttpMethod.GET, entity, GetDialModelsResponse.class);

    return response.getBody().getData().stream()
      .map(GetDialModelsResponse.ModelData::getModel)
      .toList();
  }
}
