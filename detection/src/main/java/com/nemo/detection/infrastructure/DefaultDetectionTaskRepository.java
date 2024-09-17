package com.nemo.detection.infrastructure;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.nemo.detection.domain.DetectionTask;
import com.nemo.detection.domain.DetectionTaskRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * 存储 DetectionTask 暂时放内存中，可以持久化
 *
 * @author nemo
 */
@Component
public class DefaultDetectionTaskRepository implements DetectionTaskRepository {

	private Cache<String, DetectionTask> TASKS = Caffeine.newBuilder()
	                                                     .expireAfterAccess(Duration.of(5, ChronoUnit.HOURS))
	                                                     .build();

	@Override
	public void save(DetectionTask task) {

		TASKS.put(task.getId(), task);
	}

	@Override
	public DetectionTask find(String id) {

		return TASKS.getIfPresent(id);
	}
}
