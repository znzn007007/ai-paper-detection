package com.nemo.detection.domain.openai.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 使用token的统计
 */
@Data
public class Usage implements Serializable {

	/**
	 * Number of tokens in the prompt.
	 */
	@JsonProperty("prompt_tokens")
	private long promptTokens;

	/**
	 * Number of tokens in the generated completion.
	 */
	@JsonProperty("completion_tokens")
	private long completionTokens;

	/**
	 * 总量令牌
	 */
	@JsonProperty("total_tokens")
	private long totalTokens;
}