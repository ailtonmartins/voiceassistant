package com.ailtonmartins.voiceassistant.domain;

import java.util.Arrays;

public record AssistantResponse(String answer, byte[] audio) {

	public AssistantResponse {
		if (audio == null || audio.length == 0) {
			throw new IllegalArgumentException("Audio must not be empty");
		}

		audio = Arrays.copyOf(audio, audio.length);
	}

	@Override
	public byte[] audio() {
		return Arrays.copyOf(this.audio, this.audio.length);
	}

}
