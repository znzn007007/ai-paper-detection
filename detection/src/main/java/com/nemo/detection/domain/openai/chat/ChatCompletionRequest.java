package com.nemo.detection.domain.openai.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nemo.detection.domain.Model;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * chatgpt chat接口的request
 *
 * @author nemo
 */
@Data
@Builder
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionRequest implements Serializable {

	/**
	 * 默认模型
	 */
	@Builder.Default
	private String model = Model.GPT_3_5_TURBO.getCode();

	/**
	 * 问题描述
	 */
	private List<Message> messages;

	/**
	 * 控制温度【随机性】；0到2之间。较高的值(如0.8)将使输出更加随机，而较低的值(如0.2)将使输出更加集中和确定
	 */
	@Builder.Default
	private double temperature = 0.2;

	/**
	 * 多样性控制；使用温度采样的替代方法称为核心采样，其中模型考虑具有top_p概率质量的令牌的结果。因此，0.1 意味着只考虑包含前 10% 概率质量的代币
	 */
	@Builder.Default
	@JsonProperty("top_p")
	private Double topP = 1d;

	/**
	 * 为每个提示生成的完成次数
	 */
	@Builder.Default
	private Integer n = 1;

	/**
	 * 是否为流式输出；就是一蹦一蹦的，出来结果
	 */
	@Builder.Default
	private boolean stream = false;

	/**
	 * 停止输出标识
	 */
	private List<String> stop;

	/**
	 * 输出字符串限制；0 ~ 4096
	 */
	@Builder.Default
	@JsonProperty("max_tokens")
	private Integer maxTokens = 2048;

	/**
	 * 频率惩罚；降低模型重复同一行的可能性
	 */
	@Builder.Default
	@JsonProperty("frequency_penalty")
	private double frequencyPenalty = 0;

	/**
	 * 存在惩罚；增强模型谈论新话题的可能性
	 */
	@Builder.Default
	@JsonProperty("presence_penalty")
	private double presencePenalty = 0;

	/**
	 * 生成多个调用结果，只显示最佳的。这样会更多的消耗你的 api token
	 */
	@JsonProperty("logit_bias")
	private Map logitBias;

	/**
	 * 调用标识，避免重复调用
	 */
	private String user;

	/**
	 * 是否返回对数概率
	 */
	private Boolean logprobs;
}