# Voice Assistant

Projeto de caso de estudo para uso do **Spring AI** em uma aplicacao Java baseada em **Spring Boot** e organizada com os principios de **Clean Architecture**.

## Objetivo

Este projeto tem como objetivo receber uma resposta em texto e retornar um audio gerado a partir desse conteudo.

O fluxo esperado da aplicacao e:

1. Receber uma requisicao contendo uma resposta em texto.
2. Processar a entrada na camada de aplicacao.
3. Utilizar recursos de inteligencia artificial por meio do Spring AI.
4. Gerar um audio correspondente ao texto recebido.
5. Retornar o audio para o cliente.

## Tecnologias

- Java 21
- Spring Boot
- Spring AI
- Gradle
- Docker
- Docker Compose
- Clean Architecture

## Arquitetura

O projeto sera estruturado seguindo Clean Architecture, separando responsabilidades entre regras de negocio, casos de uso, adaptadores e configuracoes externas.

Estrutura sugerida:

```text
src/main/java
└── com/ailtonmartins/voiceassistant
    ├── domain
    │   ├── CommandRequest.java
    │   ├── AssistantResponse.java
    │   └── ports
    │       ├── AiAssistantPort.java
    │       └── TextToSpeechPort.java
    ├── application
    │   └── HandleVoiceCommandUseCase.java
    ├── infrastructure
    │   └── ai
    │       ├── SpringAiAssistantAdapter.java
    │       └── OpenAiTextToSpeechAdapter.java
    └── web
        └── VoiceCommandController.java
```

## Responsabilidades Das Camadas

- **Domain**: contem os modelos principais da aplicacao, como `CommandRequest` e `AssistantResponse`, alem dos contratos `AiAssistantPort` e `TextToSpeechPort`.
- **Application**: contem o caso de uso `HandleVoiceCommandUseCase`, responsavel por coordenar o fluxo entre assistente de IA e geracao de audio.
- **Infrastructure**: contem os adapters externos, como `SpringAiAssistantAdapter` para integracao com Spring AI e `OpenAiTextToSpeechAdapter` para conversao de texto em audio.
- **Web**: contem o controller REST `VoiceCommandController`, responsavel por receber a requisicao HTTP e retornar o audio gerado.

## Requisito Funcional Inicial

### Gerar audio a partir de texto

A aplicacao deve disponibilizar um endpoint para receber um texto e retornar um arquivo de audio.

Exemplo de entrada:

```json
{
  "answer": "O Spring AI facilita a integracao de aplicacoes Java com modelos de inteligencia artificial."
}
```

Exemplo de saida:

```text
audio/mpeg
```

## Como Executar

### Com Docker Compose

Defina a chave da OpenAI no ambiente:

```bash
export OPENAI_API_KEY=sua-chave-aqui
```

Ou crie um arquivo `.env` a partir do exemplo:

```bash
cp .env.example .env
```

Depois edite o `.env` e informe sua chave:

```text
OPENAI_API_KEY=sua-chave-aqui
```

Suba a aplicacao:

```bash
docker compose up --build
```

A aplicacao ficara disponivel em:

```text
http://localhost:8080
```

Exemplo de chamada:

```bash
curl -X POST http://localhost:8080/voice-commands \
  -H "Content-Type: application/json" \
  -o assistant-response.mp3 \
  -d '{"answer":"Explique em poucas palavras o que e Spring AI."}'
```

Para parar os containers:

```bash
docker compose down
```

### Localmente

Execute a aplicacao com:

```bash
./gradlew bootRun
```

Execute os testes com:

```bash
./gradlew test
```

## Configuracao

As configuracoes da aplicacao ficam em:

```text
src/main/resources/application.yaml
src/main/resources/application-dev.yaml
```

Caso o projeto utilize um provedor externo de IA, configure as credenciais por variaveis de ambiente ou por profile local, evitando versionar chaves sensiveis.

Ao executar com Docker Compose, o profile `dev` e ativado automaticamente por `SPRING_PROFILES_ACTIVE=dev`, e a aplicacao espera receber `OPENAI_API_KEY` pelo ambiente.

Se `OPENAI_API_KEY` nao estiver definida, o Docker Compose interrompe a execucao antes de iniciar o container. Isso evita o erro de inicializacao do Spring AI informando que a chave da OpenAI nao foi configurada.

## Status

Projeto em desenvolvimento para estudo de Spring AI com Java 21, Spring Boot e Clean Architecture.
