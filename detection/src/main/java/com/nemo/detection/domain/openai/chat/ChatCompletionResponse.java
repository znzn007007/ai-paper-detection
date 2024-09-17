package com.nemo.detection.domain.openai.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * chatgpt chat接口的response
 *
 * @author nemo
 * @link https://platform.openai.com/docs/api-reference/detection/object
 */
@Data
public class ChatCompletionResponse implements Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 对象
	 */
	private String object;

	/**
	 * 模型
	 */
	private String model;

	/**
	 * 对话
	 */
	private List<ChatChoice> choices;

	/**
	 * 创建
	 */
	private long created;

	/**
	 * 耗材
	 */
	private Usage usage;

	/**
	 * 该指纹代表模型运行时使用的后端配置。
	 */
	@JsonProperty("system_fingerprint")
	private String systemFingerprint;
}
