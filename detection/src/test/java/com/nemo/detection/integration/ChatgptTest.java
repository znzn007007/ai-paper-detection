package com.nemo.detection.integration;

import com.nemo.detection.domain.Model;
import com.nemo.detection.domain.openai.chat.ChatCompletionRequest;
import com.nemo.detection.domain.openai.chat.ChatCompletionResponse;
import com.nemo.detection.domain.openai.chat.Message;
import com.nemo.detection.domain.openai.chat.Role;
import com.nemo.detection.infrastructure.session.DefaultOpenAISessionFactory;
import com.nemo.detection.infrastructure.session.OpenAIConfiguration;
import com.nemo.detection.infrastructure.session.OpenAISession;
import com.nemo.detection.infrastructure.session.OpenAISessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * @author nemo
 */
@Tag("integration")
public class ChatgptTest {

	private static OpenAISession openAISession;

	@BeforeAll
	public static void openAiSessionFactory() {

		OpenAIConfiguration configuration = new OpenAIConfiguration();
		configuration.setApiHost("https://api.openai.com/");
		configuration.setApiKey("");
		OpenAISessionFactory factory = new DefaultOpenAISessionFactory(configuration);
		openAISession = factory.openSession();
	}

	@Test
	public void test_chat_completions() {

		ChatCompletionRequest chatCompletion = ChatCompletionRequest
				.builder()
				.messages(Collections.singletonList(Message.builder().role(Role.USER).content("写一个java冒泡排序")
				                                           .build()))
				.model(Model.GPT_3_5_TURBO.getCode())
				.build();

		ChatCompletionResponse chatCompletionResponse = openAISession.chat(chatCompletion);

		chatCompletionResponse.getChoices().forEach(e -> System.out.println("test result is " + e.getMessage()));
	}
}
