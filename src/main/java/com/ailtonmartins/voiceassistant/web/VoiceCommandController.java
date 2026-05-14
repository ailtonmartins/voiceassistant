package com.ailtonmartins.voiceassistant.web;

import com.ailtonmartins.voiceassistant.application.HandleVoiceCommandUseCase;
import com.ailtonmartins.voiceassistant.domain.AssistantResponse;
import com.ailtonmartins.voiceassistant.domain.CommandRequest;
import jakarta.validation.Valid;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voice-commands")
public class VoiceCommandController {

	private static final MediaType AUDIO_MPEG = MediaType.parseMediaType("audio/mpeg");

	private final HandleVoiceCommandUseCase handleVoiceCommandUseCase;

	public VoiceCommandController(HandleVoiceCommandUseCase handleVoiceCommandUseCase) {
		this.handleVoiceCommandUseCase = handleVoiceCommandUseCase;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = "audio/mpeg")
	public ResponseEntity<byte[]> handle(@Valid @RequestBody CommandRequest request) {
		AssistantResponse response = this.handleVoiceCommandUseCase.handle(request);

		return ResponseEntity.ok()
				.contentType(AUDIO_MPEG)
				.cacheControl(CacheControl.noStore())
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"assistant-response.mp3\"")
				.body(response.audio());
	}

}
