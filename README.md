# AI Chat Bot Service

## Description
The AI Chat Bot Service is a backend application built using Spring Boot, designed to provide semantic analysis of user inputs and integrate with sophisticated AI services like OpenAI and Microsoft Semantic Kernel. The service is capable of processing user input with historical context to generate dynamic responses.

## Features
- **Semantic Analysis**: Utilize Microsoft Semantic Kernel to analyze user inputs based on past interactions and context.
- **Chat Completions**: Integrate with OpenAI to generate chat completions and dynamic conversations.
- **History Tracking**: Maintain a history of the chat to support the algorithms in generating contextual responses.

## Using the AI Chat Bot Service Controller

The AI Chat Bot Service contains two main endpoints that facilitate user interaction with AI to generate responses based on user inputs. Below are details on how to use these endpoints:

### 1. Semantic Analysis with Historical Context

- **Endpoint**: `POST /analyze`
- **Description**: This endpoint processes user inputs using semantic analysis by leveraging historical chat data. It's suitable for applications requiring context-aware conversational AI.

- **Usage**:
  - **URL**: `http://localhost:8090/analyze`
  - **Method**: POST
  - **Body**:
    ```json
    {
      "input": "Your user's message here"
    }
    ```

- **cURL Example**:
  ```bash
  curl -X POST http://localhost:8090/analyze \
    -H 'Content-Type: application/json' \
    -d '{"input": "I want to find top-10 books about world history"}'
### Expected Response

```json
{
  "messages": ["Of course! Here is a list of top 10 widely acclaimed books about world history:\n\n1. \"Sapiens: A Brief History of Humankind\" by Yuval Noah Harari\n2. \"Guns, Germs, and Steel: The Fates of Human Societies\" by Jared Diamond\n3. \"A People's History of the World\" by Chris Harman\n4. \"The Silk Roads: A New History of the World\" by Peter Frankopan\n5. \"The History of the Ancient World: From the Earliest Accounts to the Fall of Rome\" by Susan Wise Bauer\n6. \"The History of the Renaissance World: From the Rediscovery of Aristotle to the Conquest of Constantinople\" by Susan Wise Bauer\n7. \"The History of the Medieval World: From the Conversion of Constantine to the First Crusade\" by Susan Wise Bauer \n8. \"Postwar: A History of Europe Since 1945\" by Tony Judt\n9. \"The Lessons of History\" by Will Durant and Ariel Durant\n10. \"The Crusades: The Authoritative History of the War for the Holy Land\" by Thomas Asbridge\n\nThese books cover different time periods and aspects of world history, and they are all highly regarded by critics and readers alike."]
}
```


### 2. Chat Completions from OpenAI

- **Endpoint**: `POST /chat`
- **Description**: Utilizes OpenAI's language models to generate real-time chat completions and dynamic responses.

- **Usage**:
  - **URL**: `http://localhost:8090/chat`
  - **Method**: POST
  - **Body**:
```json
    {
      "input": "I want to find top-10 books about world history"
    }
```

- **cURL Example**:
  ```bash
  curl -X POST http://localhost:8090/chat \
    -H 'Content-Type: application/json' \
    -d '{"input": "I want to find top-10 books about world history"}' ```
### Expected Response

```json
{
  "messages": ["Here is a list of the top 10 books about world history:\n\n1. **\"A History of the World in 100 Objects\" by Neil MacGregor**\n   - This book provides a unique perspective on world history through the examination of 100 objects from different cultures and eras.\n\n2. **\"Guns, Germs, and Steel: The Fates of Human Societies\" by Jared Diamond**\n   - This book explores the different factors that have influenced the fates of different societies throughout history, including geography and environmental factors.\n\n3. **\"Sapiens: A Brief History of Humankind\" by Yuval Noah Harari**\n   - A popular and engaging look at the history of our species from the emergence of Homo sapiens in Africa to the present.\n\n4. **\"The Silk Roads: A New History of the World\" by Peter Frankopan**\n   - This book shifts the focus from the Western world to the East, telling the story of the importance of the silk roads throughout history.\n\n5. **\"The History of the Ancient World: From the Earliest Accounts to the Fall of Rome\" by Susan Wise Bauer**\n   - Offering a comprehensive overview of the ancient world, this book covers civilizations from Sumer to the Roman Empire.\n\n6. **\"The Story of Civilization\" by Will Durant**\n   - An extensive eleven-volume set that covers a broad spectrum of history including philosophy, religion, and the socio-economic forces that shaped the world.\n\n7. **\"The History of the World\" by J.M. Roberts**\n   - A comprehensive and detailed analysis that spans from prehistoric times to the modern day.\n\n8. **\"A People’s History of the World\" by Chris Harman**\n   - This book offers a perspective on world history from the standpoint of ordinary people rather than political leaders.\n\n9. **\"The Penguin History of the World\" by J.M. Roberts and Odd Arne Westad**\n   - Another thorough exploration of world history that has been regularly updated to include modern developments.\n\n10. **\"The Story of the World: History for the Classical Child\" by Susan Wise Bauer**\n    - A four-volume series that presents world history in a narrative format, making it accessible and interesting for younger readers (but engaging enough for adults as well!).\n\nThese books offer a variety of perspectives and methods for understanding world history, so you might choose based on what approach best suits your interests."]
}
```

### 3. Get all DIAL models

- **Endpoint**: `GET /models`
- **Description**: Gets all the models available DIAL
- **Usage**:
  - **URL**: `http://localhost:8090/models`
  - **Method**: GET

- **cURL Example**:
```bash
    curl -X GET http://localhost:8090/models
```
### Expected Response
```json
{
    [
        "gpt-35-turbo",
        "gpt-4",
        "gpt-4-turbo",
        "gpt-4o",
        "amazon.titan-tg1-large",
        "ai21.j2-jumbo-instruct",
        "anthropic.claude-v3-haiku",
        "stability.stable-diffusion-xl",
        "Llama-3-8B-Instruct",
        "Mistral-7B-Instruct",
        "chat-bison@001",
        "gemini-pro"
        ...
    ]
}
```

### 4. Chat for different models

- **Endpoint**: `POST /{modelId}/chat`
- **Description**: Utilizes OpenAI's language models to generate real-time chat completions for chosen model and dynamic responses.

- **Usage**:
  - **URL**: `http://localhost:8090/gemini-pro/chat`
  - **Method**: POST
  - **Body**:
```json
    {
      "input": "What is the weather in Odessa, Ukraine?",
      "temperature":"1.0"
    }
```

- **cURL Example**:
  ```bash
  curl -X POST http://localhost:8090/chat \
    -H 'Content-Type: application/json' \
    -d '{
            "input": "What is the weather in Odessa, Ukraine?",
            "temperature":"1.0"
        }' ```
### Expected Response

```json
{
    "messages": [
        "## Current weather in Odessa, Ukraine:\n\nAs of October 26, 2023, 11:00 PM PST:\n\n* **Temperature:** 11°C ..."
    ]
}
```

## Testing Dialogue Context Preservation with `ChatHistory`

### Objective
Verify that `ChatHistory` maintains conversational context effectively across multiple exchanges.

### Test Setup

Execute the series of API requests below and analyze the responses to ensure the chatbot recalls previously given information (like a user's name) later in the conversation.

#### Step 1: Initial Introduction
- **Request**:
  ```json
  {
    "input": "Hi, my name is Artem",
    "temperature": "1.0"
  }
  ```

#### Step 2: Unrelated Follow-Up
- **Request**:
  ```json
  {
    "input": "What is the weather?"
  }
  ```

#### Step 3: Context Recall
- **Request**:
  ```json
  {
    "input": "What is my name?"
  }
  ```

#### Expected Response:
- **Request**:
  ```json
  {
    "messages": [
      "Your name is Artem."
    ]
  }
  ```
