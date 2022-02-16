package com.proteccion.technical.test.TechnicalTest.requests;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileRequest {

	private String nameFile;

	private MultipartFile multipartFile;

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

}
