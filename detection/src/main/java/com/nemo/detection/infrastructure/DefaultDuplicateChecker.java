package com.nemo.detection.infrastructure;

import com.nemo.detection.domain.DuplicateChecker;
import org.springframework.stereotype.Component;

/**
 * 调用接口查询重复率
 * @author nemo
 */
@Component
public class DefaultDuplicateChecker implements DuplicateChecker {

	@Override
	public String check(String content) {

		return "100";
	}
}