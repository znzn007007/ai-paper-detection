package com.nemo.detection.application;

import com.nemo.detection.domain.DetectionTask;
import com.nemo.detection.domain.DetectionTaskRepository;
import com.nemo.detection.domain.DuplicateChecker;
import com.nemo.detection.domain.Model;
import com.nemo.detection.domain.openai.OpenAIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * @author nemo
 */
@ExtendWith(MockitoExtension.class)
class DetectionServiceTest {

	@Mock
	private OpenAIService chatgptService;

	@Mock
	private DuplicateChecker duplicateChecker;

	@Mock
	private DetectionTaskRepository detectionTaskRepository;

	@InjectMocks
	private DetectionService detectionService;

	private DetectionTask task;

	private List<DetectionTask> parts;

	@BeforeEach
	void setUp() {

		task = mock(DetectionTask.class);
		DetectionTask part1 = mock(DetectionTask.class);
		DetectionTask part2 = mock(DetectionTask.class);

		parts = Arrays.asList(part1, part2);
	}

	@Test
	void test_detection_withOpenAIModel() {

		when(task.splitContentIntoChunks()).thenReturn(parts);
		when(parts.get(0).getModel()).thenReturn(Model.GPT_4_32K);

		when(chatgptService.detection(parts.get(0))).thenReturn(parts.get(0));
		when(parts.get(0).result()).thenReturn("result1");
		when(parts.get(1).result()).thenReturn("result2");

		assertThat(detectionService.detection(task), is(task));

		verify(chatgptService).detection(parts.get(0));
		verify(parts.get(0)).result();
		verify(parts.get(1)).result();
		verify(detectionTaskRepository, times(2)).save(task);
	}

	@Test
	void test_checkDuplication() {

		when(duplicateChecker.check(anyString())).thenReturn("result");
		assertThat(detectionService.checkDuplication("content"), is("result"));
	}
}