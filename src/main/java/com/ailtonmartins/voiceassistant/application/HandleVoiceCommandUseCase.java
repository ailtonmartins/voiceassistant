package com.ailtonmartins.voiceassistant.application;

import com.ailtonmartins.voiceassistant.domain.AssistantResponse;
import com.ailtonmartins.voiceassistant.domain.CommandRequest;
import com.ailtonmartins.voiceassistant.domain.ports.AiAssistantPort;
import com.ailtonmartins.voiceassistant.domain.ports.TextToSpeechPort;
import org.springframework.stereotype.Service;

@Service
public class HandleVoiceCommandUseCase {

	private final AiAssistantPort aiAssistantPort;

	private final TextToSpeechPort textToSpeechPort;

	public HandleVoiceCommandUseCase(AiAssistantPort aiAssistantPort, TextToSpeechPort textToSpeechPort) {
		this.aiAssistantPort = aiAssistantPort;
		this.textToSpeechPort = textToSpeechPort;
	}

	public AssistantResponse handle(CommandRequest request) {
		String answer = this.aiAssistantPort.generateResponse(request.answer());
		byte[] audio = this.textToSpeechPort.synthesize(answer);

		return new AssistantResponse(answer, audio);
	}

}
