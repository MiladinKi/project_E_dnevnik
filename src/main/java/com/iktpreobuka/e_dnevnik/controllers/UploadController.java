package com.iktpreobuka.e_dnevnik.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.e_dnevnik.security.Views;
import com.iktpreobuka.e_dnevnik.services.FileHandler;

@Controller
@RequestMapping(path = "/")
public class UploadController {

	@JsonView(Views.Admin.class)
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FileHandler fileHandler;

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "upload";
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		String result = null;
		logger.debug("This is a debug message");
		logger.info("This is an info message");
		logger.warn("This is a warn message");
		logger.error("This is an error message");

		try {
			result = fileHandler.singleFileUpload(file, redirectAttributes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}