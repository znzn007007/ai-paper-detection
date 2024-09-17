package com.nemo.detection.infrastructure.session;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 发送http请求时额外增加apikey
 */
public class OpenAiInterceptor implements Interceptor {

	/**
	 * OpenAi apiKey
	 */
	private final String defaultApiKey;

	/**
	 * OpenAi apiKey
	 */
	public OpenAiInterceptor(String defaultApiKey) {
		this.defaultApiKey = defaultApiKey;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {

		Request original = chain.request();

		// 读取 apiKey；优先使用自己传递的 apiKey
		String apiKeyByUser = original.header("apiKey");
		String apiKey = null == apiKeyByUser ? defaultApiKey : apiKeyByUser;

		Request request = original.newBuilder()
				.url(original.url())
				.header("Authorization", "Bearer " + apiKey)
				.header("Content-Type", "application/json")
				.method(original.method(), original.body())
				.build();

		return chain.proceed(request);
	}

}
