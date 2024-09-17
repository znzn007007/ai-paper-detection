package com.nemo.detection.domain;

/**
 * @author nemo
 */
public interface DetectionTaskRepository {

	void save(DetectionTask task);

	DetectionTask find(String id);
}