package com.epam.training.gen.ai.service;


import org.springframework.stereotype.Service;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.FunctionResult;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionArguments;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for processing user input with contextual history, using semantic kernel functions to generate responses.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KernelService {

  private final Kernel kernel;
  private final ChatHistory chatHistory;
  /**
   * Processes user input using historical context and invokes the system kernel. Adds messages from both the user and the assistant to the chat history.
   *
   * @param input User-supplied string to process.
   * @return Processed response as a string containing the generated answer.
   */
  public String processInputHistorically(final String input) {
    final InvocationContext context = new InvocationContext.Builder()
      .withReturnMode(InvocationReturnMode.LAST_MESSAGE_ONLY)
      .withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true))
      .build();

    final FunctionResult<String> result = kernel.invokeAsync(createChatKernelFunction())
      .withArguments(defineKernelArguments(input, chatHistory))
      .withInvocationContext(context)
      .block();

    // Logging messages into chat history.
    chatHistory.addUserMessage(input);
    chatHistory.addAssistantMessage(result.getResult());

    log.info("Processed Response: {}", result.getResult());
    return result.getResult();
  }

  /**
   * Creates a kernel function for processing chat interactions.
   *
   * @return Configured KernelFunction to handle the submitted message.
   */
  private KernelFunction<String> createChatKernelFunction() {
    return KernelFunction.<String>createFromPrompt("""
        {{$chatHistory}}
        <message role="user">{{$input}}</message>""")
      .build();
  }

  /**
   * Defines the arguments for the kernel function based on the user input and the chat history.
   *
   * @param input User's input string entered in the chat.
   * @param history Chat history record.
   * @return KernelFunctionArguments object containing arguments for the kernel function.
   */
  private KernelFunctionArguments defineKernelArguments(String input, ChatHistory history) {
    return KernelFunctionArguments.builder()
      .withVariable("input", input)
      .withVariable("chatHistory", history)
      .build();
  }
}
