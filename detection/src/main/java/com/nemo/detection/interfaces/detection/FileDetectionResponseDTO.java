package com.nemo.detection.interfaces.detection;

import lombok.Builder;
import lombok.Data;

/**
 * @author nemo
 */
@Data
@Builder
public class FileDetectionResponseDTO {

	private String original;

	private String processed;
}
