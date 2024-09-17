package com.nemo.detection.domain.openai.chat;

/**
 * open AI 中的角色
 *
 * @author nemo
 */
public enum Role {

	SYSTEM("system"), USER("user"), ASSISTANT("assistant");

	private String code;

	Role(String code) {

		this.code = code;
	}

	public String getCode() {

		return code;
	}
}
