package com.nemo.detection.infrastructure.session;

/**
 * 用来创建不同api的session的工厂
 */
public interface OpenAISessionFactory {
	OpenAISession openSession();
}
