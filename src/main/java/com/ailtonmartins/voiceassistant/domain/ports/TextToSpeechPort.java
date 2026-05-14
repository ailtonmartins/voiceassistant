package com.ailtonmartins.voiceassistant.domain.ports;

public interface TextToSpeechPort {

	byte[] synthesize(String text);

}
