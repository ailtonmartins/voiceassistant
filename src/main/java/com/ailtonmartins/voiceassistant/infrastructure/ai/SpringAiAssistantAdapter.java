package com.ailtonmartins.voiceassistant.infrastructure.ai;

import com.ailtonmartins.voiceassistant.domain.ports.AiAssistantPort;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SpringAiAssistantAdapter implements AiAssistantPort {

	private static final String SYSTEM_PROMPT = "Voce e um assistente objetivo. Responda com tom intenso, confiante e dramático, " +
			                                    "como um guerreiro arrogante" +
		                                    	" de anime do dragon ball, sem imitar personagens existentes e use algum bordão dele.";

	private final ChatClient chatClient;

	public SpringAiAssistantAdapter(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder
				.defaultSystem(SYSTEM_PROMPT)
				.build();
	}

	@Override
	public String generateResponse(String command) {
		if (!StringUtils.hasText(command)) {
			throw new IllegalArgumentException("Command must not be blank");
		}

		return this.chatClient
				.prompt()
				.user(command)
				.call()
				.content();
	}

}
