package com.common.uploadback.controller;

import com.common.uploadback.UploadFileResponse;
import com.common.uploadback.repository.UploadFileResponseRepository;
import com.common.uploadback.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/fileStorage")
@CrossOrigin
public class FileStorageController {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    @Autowired
    UploadFileResponseRepository uploadFileResponseRepository;
    // todo Ver como insertar el servicio de fileStorageService

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        FileStorageService fileStorageService = new FileStorageService(); // aca se podria elegir el path
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/fileStorage/downloadFile/")
                .path(fileName)
                .toUriString();
        UploadFileResponse uploadFileResponse = new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
        return uploadFileResponseRepository.save(uploadFileResponse);
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        FileStorageService fileStorageService = new FileStorageService(); // aca se podria elegir el path
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
