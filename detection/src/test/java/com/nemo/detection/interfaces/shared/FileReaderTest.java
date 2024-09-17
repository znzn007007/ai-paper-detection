package com.nemo.detection.interfaces.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author nemo
 */
class FileReaderTest {

	private FileReader fileReader;

	@BeforeEach
	public void setUp() {

		fileReader = new FileReader();
	}

	@Test
	public void readWithEmptyFilename() {

		MultipartFile mockFile = mock(MultipartFile.class);
		when(mockFile.getOriginalFilename()).thenReturn("");

		RuntimeException exception = assertThrows(RuntimeException.class, () -> fileReader.read(mockFile));

		assertEquals("no file", exception.getMessage());
	}

	@Test
	public void readWithPdfFile() throws IOException {

		MultipartFile mockFile = mock(MultipartFile.class);
		when(mockFile.getOriginalFilename()).thenReturn("test.pdf");
		InputStream mockInputStream = new ByteArrayInputStream(new byte[0]);
		when(mockFile.getInputStream()).thenReturn(mockInputStream);

		FileReader spyReader = spy(fileReader);
		doReturn("pdf content").when(spyReader).readPdf(mockInputStream);

		String result = spyReader.read(mockFile);
		assertEquals("pdf content", result);

		verify(spyReader, times(1)).readPdf(mockInputStream);
	}

	@Test
	public void readWithDocFile() throws IOException {

		MultipartFile mockFile = mock(MultipartFile.class);
		when(mockFile.getOriginalFilename()).thenReturn("test.doc");
		InputStream mockInputStream = new ByteArrayInputStream(new byte[0]);
		when(mockFile.getInputStream()).thenReturn(mockInputStream);

		FileReader spyReader = spy(fileReader);
		doReturn("doc content").when(spyReader).readDoc(mockInputStream);

		String result = spyReader.read(mockFile);
		assertEquals("doc content", result);

		verify(spyReader, times(1)).readDoc(mockInputStream);
	}

	@Test
	public void readWithDocxFile() throws IOException {

		MultipartFile mockFile = mock(MultipartFile.class);
		when(mockFile.getOriginalFilename()).thenReturn("test.docx");
		InputStream mockInputStream = new ByteArrayInputStream(new byte[0]);
		when(mockFile.getInputStream()).thenReturn(mockInputStream);

		FileReader spyReader = spy(fileReader);
		doReturn("doc content").when(spyReader).readDocx(mockInputStream);

		String result = spyReader.read(mockFile);
		assertEquals("doc content", result);

		verify(spyReader, times(1)).readDocx(mockInputStream);
	}

	@Test
	public void readWithUnsupportedFile() {

		MultipartFile mockFile = mock(MultipartFile.class);
		when(mockFile.getOriginalFilename()).thenReturn("test.txt");

		RuntimeException exception = assertThrows(RuntimeException.class, () -> fileReader.read(mockFile));

		assertEquals("file is not supported", exception.getMessage());
	}
}