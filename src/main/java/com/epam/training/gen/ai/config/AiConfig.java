package com.epam.training.gen.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.epam.training.gen.ai.plugin.SimplePlugin;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
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
  public KernelPlugin kernelPlugin() {
    return KernelPluginFactory.createFromObject(
      new SimplePlugin(), "SimplePlugin");
  }

  @Bean
  public Kernel kernel(
    final ChatCompletionService chatCompletionService,
    final KernelPlugin kernelPlugin
  ) {
    return Kernel.builder()
      .withAIService(ChatCompletionService.class, chatCompletionService)
      .withPlugin(kernelPlugin)
      .build();
  }

  @Bean
  @Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
  public ChatHistory chatHistory() {
    return new ChatHistory();
  }
}
