package com.nemo.detection.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nemo
 */
@RestController
@RequestMapping("/")
public class Index {

	@GetMapping("/heartbeat")
	public String heartbeat() {

		return "welcome to Nautilus!";
	}
}
