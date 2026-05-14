package com.ailtonmartins.voiceassistant.domain;

import org.springframework.util.StringUtils;

public record CommandRequest(String answer) {

	public CommandRequest {
		if (!StringUtils.hasText(answer)) {
			throw new IllegalArgumentException("Answer must not be blank");
		}
	}

}
