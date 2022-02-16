package com.proteccion.technical.test.TechnicalTest.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proteccion.technical.test.TechnicalTest.requests.UploadFileRequest;
import com.proteccion.technical.test.TechnicalTest.services.AppService;

@RestController
public class AppController {
	
	@Autowired
	private AppService service;

	@PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void saveFile(@ModelAttribute UploadFileRequest request) throws IOException {
		service.saveFile(request);		
	}
	
}
