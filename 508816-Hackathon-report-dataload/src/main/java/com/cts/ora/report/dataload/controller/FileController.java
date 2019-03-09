package com.cts.ora.report.dataload.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cts.ora.report.common.util.ORAMessageUtil;
import com.cts.ora.report.dataload.service.FileService;
import com.cts.ora.report.dataload.vo.ORAFileResponse;

@RestController
@RequestMapping("/api/file")
public class FileController {

	Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileService fileService;

	@PostMapping("/upload")
	@ResponseBody
	public ORAFileResponse uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		logger.info("Into uploadFile");
		ORAFileResponse resp = new ORAFileResponse();
		try {
			StopWatch watch = new StopWatch();
			watch.start();

			if (file.isEmpty()) {
				ORAMessageUtil.setMessage(resp, "Please select a valid file", "0001", "FAILURE");
				return resp;
			}
			resp = fileService.saveFile(file);

			watch.stop();
			logger.info("out of uploadFile");
			logger.info("UploadFile Time taken(seconds)==" + watch.getTotalTimeSeconds());
		} catch (Exception e) {
			logger.error(e.getMessage());
			ORAMessageUtil.setFailureMessage(resp);
		}
		logger.info("Out of uploadFile");
		return resp;
	}

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		logger.info("Into downloadFile");
		// Load file as Resource
		Resource resource = fileService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		logger.info("Out of downloadFile");
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
