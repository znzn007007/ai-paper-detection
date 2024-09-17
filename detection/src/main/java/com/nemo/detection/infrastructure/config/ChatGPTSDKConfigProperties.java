package com.nemo.detection.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * spring 读取配置信息
 * @author nemo
 */
@Data
@ConfigurationProperties(prefix = "chatgpt.sdk.config", ignoreInvalidFields = true)
public class ChatGPTSDKConfigProperties {

	/**
	 * 状态；open = 开启、close 关闭
	 */
	private boolean enable;
	/**
	 * 转发地址
	 */
	private String apiHost;
	/**
	 * chatgpt apikey
	 */
	private String apiKey;
}