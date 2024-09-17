package com.nemo.detection.interfaces.detection;

import com.nemo.detection.domain.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 承接前端过来的参数
 * @author nemo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetectionRequestDTO {

	private Model model;

	private String content;
}
