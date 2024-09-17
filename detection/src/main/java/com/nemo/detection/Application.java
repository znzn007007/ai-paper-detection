package com.nemo.detection;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author nemo
 */
@SpringBootApplication(scanBasePackages = {"com.nemo.detection"})
@Configurable
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}
}