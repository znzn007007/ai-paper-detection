package com.nemo.detection.domain;

import com.nemo.detection.domain.openai.chat.ChatCompletionResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * 业务中可能使用的chat 参数以及响应
 *
 * @author nemo
 */
@Data
@SuperBuilder
public class ChatInfo {

	private Model model;

	private String content;

	private ChatCompletionResponse response;
}
