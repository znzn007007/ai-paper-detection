package com.nemo.detection.infrastructure.session;

import com.nemo.detection.domain.openai.chat.ChatCompletionRequest;
import com.nemo.detection.domain.openai.chat.ChatCompletionResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * openAI 的 接口封装，使用retrofit2
 */
public interface OpenAIApi {

	String v1_chat_completions = "v1/chat/completions";

	@POST(v1_chat_completions)
	Single<ChatCompletionResponse> completions(@Body ChatCompletionRequest chatCompletionRequest);
}