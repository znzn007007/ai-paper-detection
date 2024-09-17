package com.nemo.detection.domain;

import com.nemo.detection.domain.openai.chat.ChatChoice;
import com.nemo.detection.domain.openai.chat.ChatCompletionResponse;
import com.nemo.detection.domain.openai.chat.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author nemo
 */
@ExtendWith(MockitoExtension.class)
class DetectionTaskTest {

	@Mock
	private Model model;

	@Mock
	private ChatCompletionResponse response;

	private DetectionTask detectionTask;

	@BeforeEach
	void setUp() {

		detectionTask = DetectionTask.builder()
		                             .model(model)
		                             .content("This is a test content to be split into chunks. It should handle edge cases and normal cases correctly.")
		                             .id("testId")
		                             .build();
	}

	@Test
	void test_result_withValidResponse() {

		ChatChoice choice = mock(ChatChoice.class);
		Message message = mock(Message.class);

		detectionTask.setResponse(response);
		when(response.getChoices()).thenReturn(List.of(choice));
		when(choice.getMessage()).thenReturn(message);
		when(message.getContent()).thenReturn("Polished content");

		String result = detectionTask.result();

		assertEquals("Polished content", result);
	}

	@Test
	void test_result_withNullResponse() {

		detectionTask.setResponse(null);

		String result = detectionTask.result();

		assertEquals("", result);
	}

	@Test
	void test_splitContentIntoChunks() {

		when(model.getTokenLimit()).thenReturn(3750);

		List<DetectionTask> chunks = detectionTask.splitContentIntoChunks();

		assertEquals(1, chunks.size());
		assertEquals("testId", chunks.get(0).getId());
		assertTrue(chunks.get(0).getContent().contains("This is a test content to be split into chunks. It should handle edge cases and normal cases correctly."));
	}

	@Test
	void test_splitContentIntoChunks_withMoreWords() {

		when(model.getTokenLimit()).thenReturn(20);

		List<DetectionTask> chunks = detectionTask.splitContentIntoChunks();

		assertEquals(3, chunks.size());
		assertEquals("testId_0", chunks.get(0).getId());
		assertEquals("testId_1", chunks.get(1).getId());
		assertEquals("testId_2", chunks.get(2).getId());
		assertTrue(chunks.get(0).getContent().contains("Below is a paragraph from an academic paper."));
	}
}