package com.nemo.detection.infrastructure.session;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

/**
 * @author nemo
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenAIConfiguration {

	private OpenAIApi openAIApi;

	private OkHttpClient okHttpClient;

	private String apiKey;

	private String apiHost;
}