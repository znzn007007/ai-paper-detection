package com.nemo.detection.infrastructure.session;


import com.nemo.detection.domain.openai.chat.ChatCompletionRequest;
import com.nemo.detection.domain.openai.chat.ChatCompletionResponse;

public interface OpenAISession {

	/**
	 * 单次反馈
	 * @param chatCompletionRequest
	 * @return
	 */
	ChatCompletionResponse chat(ChatCompletionRequest chatCompletionRequest);
}
