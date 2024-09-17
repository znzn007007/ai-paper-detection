package com.nemo.detection.infrastructure.session;


import com.nemo.detection.domain.openai.chat.ChatCompletionRequest;
import com.nemo.detection.domain.openai.chat.ChatCompletionResponse;

/**
 * @author nemo
 */
public class DefaultOpenAISession implements OpenAISession {

	/**
	 * OpenAI 接口
	 */
	private final OpenAIApi openAIApi;

	public DefaultOpenAISession(OpenAIConfiguration openAIConfiguration) {
		this.openAIApi = openAIConfiguration.getOpenAIApi();
	}

	@Override
	public ChatCompletionResponse chat(ChatCompletionRequest chatCompletionRequest) {
		return this.openAIApi.completions(chatCompletionRequest).blockingGet();
	}
}
