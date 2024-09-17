package com.nemo.detection.interfaces.detection;

import com.nemo.detection.application.DetectionService;
import com.nemo.detection.domain.DetectionTask;
import com.nemo.detection.domain.Model;
import com.nemo.detection.interfaces.shared.FileReader;
import com.nemo.detection.interfaces.shared.Response;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author nemo
 */
@RestController
@RequestMapping("/detections")
public class DetectionController {

	@Resource
	private DetectionService detectionService;

	@Resource
	private FileReader fileReader;

	@PostMapping("/text")
	public Response text(@RequestBody DetectionRequestDTO dto) {

		DetectionTask task = DetectionTask.builder().model(dto.getModel()).content(dto.getContent())
		                                  .id(String.valueOf(new Date().getTime())).build();
		String combinedResult = detectionService.detection(task).getCombinedResult();
		return Response.ok(combinedResult);
	}

	@PostMapping("/file")
	public Response file(@RequestParam("file") MultipartFile file, @RequestParam("model") Model model) throws Exception {

		DetectionTask task = DetectionTask.builder().model(model).content(fileReader.read(file))
		                                  .id(String.valueOf(new Date().getTime())).build();
		DetectionTask result = detectionService.detection(task);
		FileDetectionResponseDTO dto = FileDetectionResponseDTO.builder().original(result.getContent())
		                                                       .processed(result.getCombinedResult()).build();
		return Response.ok(dto);
	}

	/**
	 * 实际上是个空接口，为了前端展示效果，睡3s
	 */
	@PostMapping("/duplication")
	public Response checkDuplication(String content) throws Exception {

		Thread.sleep(3000);
		return Response.ok(detectionService.checkDuplication(content));
	}
}