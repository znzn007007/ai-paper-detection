package com.nemo.detection.infrastructure.config;

import com.nemo.detection.infrastructure.session.OpenAISession;
import com.nemo.detection.infrastructure.session.OpenAISessionFactory;
import com.nemo.detection.infrastructure.session.DefaultOpenAISessionFactory;
import com.nemo.detection.infrastructure.session.OpenAIConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * chatgpt 配置类
 * @author nemo
 */
@Configuration
@EnableConfigurationProperties(ChatGPTSDKConfigProperties.class)
public class ChatGPTSDKConfig {

	@Bean(name = "chatGPTOpenAISession")
	@ConditionalOnProperty(value = "chatgpt.sdk.config.enabled", havingValue = "true")
	public OpenAISession openAISession(ChatGPTSDKConfigProperties properties) {

		OpenAIConfiguration openAIConfiguration = new OpenAIConfiguration();
		openAIConfiguration.setApiHost(properties.getApiHost());
		openAIConfiguration.setApiKey(properties.getApiKey());

		OpenAISessionFactory factory = new DefaultOpenAISessionFactory(openAIConfiguration);

		return factory.openSession();
	}
}