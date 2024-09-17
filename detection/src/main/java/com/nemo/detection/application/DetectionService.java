package com.nemo.detection.application;

import com.nemo.detection.domain.DetectionTask;
import com.nemo.detection.domain.DetectionTaskRepository;
import com.nemo.detection.domain.DuplicateChecker;
import com.nemo.detection.domain.Model;
import com.nemo.detection.domain.openai.OpenAIService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nemo
 */
@Service
@Slf4j
public class DetectionService {

	@Resource
	private OpenAIService chatgptService;

	@Resource
	private DetectionTaskRepository detectionTaskRepository;

	@Resource
	private DuplicateChecker duplicateChecker;

	public DetectionTask detection(DetectionTask task) {

		detectionTaskRepository.save(task);
		long start = System.currentTimeMillis();
		List<DetectionTask> chunks = task.splitContentIntoChunks();
		log.info("spilt content cost {}", System.currentTimeMillis() - start);
		String combinedResult = chunks.stream().map(t -> {
			if (Model.openAI(t.getModel())) {
				return chatgptService.detection(t).result();
			}
			return t.result();
		}).reduce("", (acc, t) -> acc + t);
		task.setCombinedResult(combinedResult);

		detectionTaskRepository.save(task);
		return task;
	}

	public String checkDuplication(String content) {

		return duplicateChecker.check(content);
	}
}