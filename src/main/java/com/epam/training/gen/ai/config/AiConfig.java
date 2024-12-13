package com.epam.training.gen.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.client.RestTemplate;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AiConfig {

  private final AppProperties appProperties;

  @Bean
  public OpenAIAsyncClient openAIAsyncClient() {
    return new OpenAIClientBuilder()
      .credential(new AzureKeyCredential(appProperties.getClientAzureopenaiKey()))
      .endpoint(appProperties.getClientAzureopenaiEndpoint())
      .buildAsyncClient();
  }

  @Bean
  public ChatCompletionService chatCompletionService(
    final OpenAIAsyncClient openAIAsyncClient
  ) {
    return OpenAIChatCompletion.builder()
      .withModelId(appProperties.getClientAzureopenaiDeploymentName())
      .withOpenAIAsyncClient(openAIAsyncClient)
      .build();
  }

  @Bean
  public Kernel kernel(
    final ChatCompletionService chatCompletionService
  ) {
    return Kernel.builder()
      .withAIService(ChatCompletionService.class, chatCompletionService)
      .build();
  }

  @Bean
  @Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
  public ChatHistory chatHistory() {
    return new ChatHistory();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
