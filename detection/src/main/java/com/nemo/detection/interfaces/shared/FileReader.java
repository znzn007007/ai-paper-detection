package com.nemo.detection.interfaces.shared;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 提取上传的文件的内容
 *
 * @author nemo
 */
@Component
public class FileReader {

	public String read(MultipartFile file) throws IOException {

		String filename = file.getOriginalFilename();
		if (StringUtils.isEmpty(filename)) {
			throw new RuntimeException("no file");
		}
		if (filename.endsWith(".pdf")) {
			return readPdf(file.getInputStream());
		} else if (filename.endsWith(".doc")) {
			return readDoc(file.getInputStream());
		} else if (filename.endsWith(".docx")) {
			return readDocx(file.getInputStream());
		} else {
			throw new RuntimeException("file is not supported");
		}
	}

	String readDocx(InputStream inputStream) throws IOException {

		StringBuilder stringBuilder = new StringBuilder();

		XWPFDocument document = new XWPFDocument(inputStream);
		for (XWPFParagraph paragraph : document.getParagraphs()) {
			for (XWPFRun run : paragraph.getRuns()) {
				stringBuilder.append(run.getText(0));
			}
			stringBuilder.append(System.lineSeparator());
		}
		return stringBuilder.toString();
	}

	String readDoc(InputStream inputStream) throws IOException {

		HWPFDocument document = new HWPFDocument(inputStream);
		WordExtractor extractor = new WordExtractor(document);
		String text = extractor.getText();
		document.close();
		return text;
	}

	String readPdf(InputStream inputStream) throws IOException {

		PDDocument document = PDDocument.load(inputStream);
		PDFTextStripper pdfStripper = new PDFTextStripper();
		String text = pdfStripper.getText(document);
		document.close();
		return text;
	}
}