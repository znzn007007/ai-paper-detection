package com.nemo.detection.infrastructure.session;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author nemo
 */
public class DefaultOpenAISessionFactory implements OpenAISessionFactory {

	private final OpenAIConfiguration openAIConfiguration;

	public DefaultOpenAISessionFactory(OpenAIConfiguration openAIConfiguration) {
		this.openAIConfiguration = openAIConfiguration;
	}

	@Override
	public OpenAISession openSession() {

		// 日志配置
		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
		httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		// 开启 Http 客户端
		OkHttpClient okHttpClient = new OkHttpClient
				.Builder()
				.addInterceptor(httpLoggingInterceptor)
				.addInterceptor(new OpenAiInterceptor(openAIConfiguration.getApiKey()))
				.connectTimeout(450, TimeUnit.SECONDS)
				.writeTimeout(450, TimeUnit.SECONDS)
				.readTimeout(450, TimeUnit.SECONDS)
				.build();
		openAIConfiguration.setOkHttpClient(okHttpClient);

		// 创建 API 服务
		OpenAIApi openAIApi = new Retrofit.Builder()
				.baseUrl(openAIConfiguration.getApiHost())
				.client(okHttpClient)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(JacksonConverterFactory.create())
				.build().create(OpenAIApi.class);
		openAIConfiguration.setOpenAIApi(openAIApi);

		return new DefaultOpenAISession(openAIConfiguration);
	}
}
