package com.nemo.detection.domain.openai;

import com.nemo.detection.domain.DetectionTask;
import com.nemo.detection.domain.openai.chat.ChatCompletionRequest;
import com.nemo.detection.domain.openai.chat.Message;
import com.nemo.detection.domain.openai.chat.Role;
import com.nemo.detection.infrastructure.session.OpenAISession;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * chatgpt 通信
 *
 * @author nemo
 */
@Service
@AllArgsConstructor
public class OpenAIService {

	@Resource(name = "chatGPTOpenAISession")
	private OpenAISession session;

	public DetectionTask detection(DetectionTask task) {

		ChatCompletionRequest request = ChatCompletionRequest.builder()
		                                                     .messages(Collections.singletonList(Message.builder()
		                                                                                                .role(Role.USER)
		                                                                                                .content(task.getContent())
		                                                                                                .build()))
		                                                     .model(task.getModel().getCode()).build();
		task.setResponse(session.chat(request));
		return task;
	}
}