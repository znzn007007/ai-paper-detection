package com.nemo.detection.domain;

import com.nemo.detection.domain.openai.chat.ChatCompletionResponse;
import com.nemo.detection.domain.util.TokenCountUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 去重任务的聚合根
 *
 * @author nemo
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class DetectionTask extends ChatInfo {

	private static final String PROMPT_PREFIX = "Below is a paragraph from an academic paper. Polish the writing to meet the academic style, improve the spelling, grammar, clarity, concision and overall readability. When necessary, rewrite the whole sentence. you should provide the polished paragraph." + "\n\n";

	private String id;

	@Builder.Default
	private String combinedResult = "";

	/**
	 * 单次请求允许的最大token
	 */
	private static final int MAX_TOKENS = 3000;

	public String result() {

		ChatCompletionResponse response = super.getResponse();
		if (response != null) {
			return response.getChoices().get(0).getMessage().getContent();
		} else {
			return "";
		}
	}

	/**
	 * 如果是文件传入，可能存在文本过长的问题，此时需要按照token上限进行分段
	 * 存在以下问题：
	 * 1. 只适用于英文
	 * 2. tokenLimit的设计不太合理
	 * 3. 因为需要多次计算token，整体性能应该不是很高 !!! 确实不太高
	 */
	public List<DetectionTask> splitContentIntoChunks() {

		Model model = getModel();
		String content = getContent();
		double tokenLimit = model.getTokenLimit() * 0.8;
		if (TokenCountUtil.countTokens(content, model) < tokenLimit) {
			return List.of(this);
		}

		int index = 0;
		List<DetectionTask> chunks = new ArrayList<>();
		String[] words = content.split(" ");
		StringBuilder segment = new StringBuilder();
		// 因为除了content消耗token，请求的其他内容也会消耗，所以给出一个80%使用量，但是这个并不一定合理

		int currentSegmentTokens = 0;

		for (String word : words) {
			// 为空格+1
			int wordTokens = TokenCountUtil.countTokens(word, model) + 1;

			if (currentSegmentTokens + wordTokens > tokenLimit) {
				// 当前segment满了以后，新建一个
				String subId = id + "_" + index;
				chunks.add(createDetectionTask(model, PROMPT_PREFIX + segment, subId));
				index++;
				segment = new StringBuilder(word);
				currentSegmentTokens = wordTokens;
			} else {
				if (!segment.isEmpty()) {
					segment.append(" ");
				}
				segment.append(word);
				currentSegmentTokens += wordTokens;
			}
		}

		if (!segment.isEmpty()) {
			String subId = id + "_" + index;
			chunks.add(createDetectionTask(model, PROMPT_PREFIX + segment, subId));
		}

		return chunks;
	}

	private DetectionTask createDetectionTask(Model model, String content, String id) {

		return DetectionTask.builder().model(model).content(content).id(id).build();
	}
}