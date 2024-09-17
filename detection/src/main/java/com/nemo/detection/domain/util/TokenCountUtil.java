package com.nemo.detection.domain.util;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingType;
import com.nemo.detection.domain.Model;

/**
 * 根据openai的token计算规则来计算消耗的token数
 * https://cookbook.openai.com/examples/how_to_count_tokens_with_tiktoken
 *
 * @author nemo
 */
public class TokenCountUtil {

	public static int countTokens(String text, Model model) {

		EncodingType encodingType = getEncodingType(model);
		Encoding encoding = Encodings.newDefaultEncodingRegistry().getEncoding(encodingType);
		return encoding.encode(text).size();
	}

	private static EncodingType getEncodingType(Model model) {

		return switch (model) {
			case GPT_4, GPT_4o, GPT_4_32K, GPT_3_5_TURBO -> EncodingType.CL100K_BASE;
		};
	}
}
