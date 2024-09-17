package com.nemo.detection.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 调用的模型
 *
 * @author nemo
 */
@Getter
@AllArgsConstructor
public enum Model {

	/**
	 * gpt-3.5-turbo
	 */
	GPT_3_5_TURBO("gpt-3.5-turbo", 4096),
	/**
	 * GPT4.0
	 */
	GPT_4("gpt-4", 8192),
	GPT_4o("gpt-4o", 128000),
	/**
	 * GPT4.0 超长上下文
	 */
	GPT_4_32K("gpt-4-32k", 32768);

	private static final List<Model> GPT_MODELS = List.of(GPT_4, GPT_4o, GPT_4_32K, GPT_3_5_TURBO);

	private final String code;

	private final int tokenLimit;

	public static boolean openAI(Model model) {

		return model != null && GPT_MODELS.contains(model);
	}
}
