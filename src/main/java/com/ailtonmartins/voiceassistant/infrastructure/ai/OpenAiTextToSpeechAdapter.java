package com.ailtonmartins.voiceassistant.infrastructure.ai;

import com.ailtonmartins.voiceassistant.domain.ports.TextToSpeechPort;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OpenAiTextToSpeechAdapter implements TextToSpeechPort {

	private static final String DEFAULT_MODEL = OpenAiAudioApi.TtsModel.TTS_1.value;
	private static final OpenAiAudioApi.SpeechRequest.Voice DEFAULT_VOICE = OpenAiAudioApi.SpeechRequest.Voice.ONYX;
	private static final OpenAiAudioApi.SpeechRequest.AudioResponseFormat DEFAULT_FORMAT =
			OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3;

	private final OpenAiAudioSpeechModel speechModel;

	public OpenAiTextToSpeechAdapter(OpenAiAudioSpeechModel speechModel) {
		this.speechModel = speechModel;
	}

	@Override
	public byte[] synthesize(String text) {
		if (!StringUtils.hasText(text)) {
			throw new IllegalArgumentException("Text must not be blank");
		}

		OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
				.model(DEFAULT_MODEL)
				.voice(DEFAULT_VOICE)
				.responseFormat(DEFAULT_FORMAT)
				.build();

		TextToSpeechPrompt prompt = new TextToSpeechPrompt(text, options);

		return this.speechModel.call(prompt)
				.getResult()
				.getOutput();
	}

}
