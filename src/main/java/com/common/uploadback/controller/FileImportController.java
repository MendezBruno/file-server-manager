package com.common.uploadback.controller;

import com.common.uploadback.FileImports;
import com.common.uploadback.service.FileUploadService;
import com.common.uploadback.repository.FileImportRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;


@RestController
@RequestMapping("/api/v1/fileImport")
@CrossOrigin
public class FileImportController {

    private static final Logger logger = LoggerFactory.getLogger(FileImportController.class);

    @Autowired
    FileImportRepository repo;



    @GetMapping("/fileImport/{id}")
    public FileImports getMap(@PathVariable ObjectId id) {
        Optional<FileImports> fileImports = repo.findById(id.toHexString());
        if (fileImports.isPresent()) {
            return fileImports.get();
        } else {
            System.out.println("No existe!!");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron datos el archivo");
        }


    }

    @PostMapping
    public FileImports saveMap(@RequestBody FileImports fileImports) {
       return repo.save(fileImports);
    }

    @PostMapping("/uploadToServer")
    public void uploadToServer() {

        FileUploadService fileUploadService = new FileUploadService(new RestTemplateBuilder());
        //byte[] someBytes = fileUploadService.readFileToByteArray(new File(UNARGUMENTOQUETODAVINANOSEUQEES);
        //FileImports fileImports = fileUploadService.postFile(UNARGUMENTOQUETODAVINANOSEUQEES, someBytes);
        //repo.save(fileImports);
    }



}
