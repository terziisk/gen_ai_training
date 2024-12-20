package com.epam.training.gen.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.client.RestTemplate;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.ClientOptions;
import com.azure.core.util.MetricsOptions;
import com.azure.core.util.TracingOptions;
import com.azure.search.documents.indexes.SearchIndexAsyncClient;
import com.azure.search.documents.indexes.SearchIndexClientBuilder;
import com.epam.training.gen.ai.plugin.LightsPlugin;
import com.epam.training.gen.ai.plugin.SimplePlugin;
import com.epam.training.gen.ai.plugin.StorePersonalDataPlugin;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.aiservices.openai.textembedding.OpenAITextEmbeddingGenerationService;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AiConfig {

  public static final int EMBEDDING_DIMENSIONS = 1536;
  private final AppProperties appProperties;

  @Bean
  public OpenAIAsyncClient openAIAsyncClient() {
    return new OpenAIClientBuilder()
      .credential(new AzureKeyCredential(appProperties.getClientAzureopenaiKey()))
      .endpoint(appProperties.getClientAzureopenaiEndpoint())
      .buildAsyncClient();
  }

  @Bean
  public OpenAITextEmbeddingGenerationService openAITextEmbeddingGenerationService(
    final OpenAIAsyncClient client
  ) {
    return OpenAITextEmbeddingGenerationService.builder()
      .withOpenAIAsyncClient(client)
      .withModelId(appProperties.getClientEmbeddedModelId())
      .withDimensions(EMBEDDING_DIMENSIONS)
      .build();
  }

  @Bean
  public SearchIndexAsyncClient searchIndexClientBuilder() {
    return  new SearchIndexClientBuilder()
      .endpoint(appProperties.getClientAzureopenaiEndpoint())
      .credential(new AzureKeyCredential(appProperties.getClientAzureopenaiKey()))
      .clientOptions(clientOptions())
      .buildAsyncClient();
  }

  private static ClientOptions clientOptions() {
    return new ClientOptions()
      .setTracingOptions(new TracingOptions())
      .setMetricsOptions(new MetricsOptions())
      .setApplicationId("Semantic-Kernel");
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
  public KernelPlugin kernelSimplePlugin() {
    return KernelPluginFactory.createFromObject(new SimplePlugin(), "SimplePlugin");
  }

  @Bean
  public KernelPlugin kernelLightsPlugin() {
    return KernelPluginFactory.createFromObject(new LightsPlugin(), "LightsPlugin");
  }

  @Bean
  public KernelPlugin kernelStorePersonalDataPlugin() {
    return KernelPluginFactory.createFromObject(new StorePersonalDataPlugin(), "StorePersonalDataPlugin");
  }

  @Bean
  public Kernel kernel(
    final ChatCompletionService chatCompletionService,
    final KernelPlugin kernelSimplePlugin,
    final KernelPlugin kernelLightsPlugin,
    final KernelPlugin kernelStorePersonalDataPlugin
  ) {
    return Kernel.builder()
      .withAIService(ChatCompletionService.class, chatCompletionService)
      .withPlugin(kernelSimplePlugin)
      .withPlugin(kernelLightsPlugin)
      .withPlugin(kernelStorePersonalDataPlugin)
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
