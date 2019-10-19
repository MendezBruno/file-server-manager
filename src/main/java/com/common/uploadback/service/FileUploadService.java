package com.common.uploadback.service;

import com.common.uploadback.FileImports;
import com.common.uploadback.FileImportsResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@Service
public class FileUploadService {

    //esta es la url a donde lo quiero subir
    private static String APIURL = "http://192.168.35.103:9000/api/file-imports/upload";

    private RestTemplate restTemplate;

    @Autowired
    public FileUploadService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        this.restTemplate.setMessageConverters(getMessageConverters());

    }

    public FileImports postFile(String filename, byte[] someByteArray) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MULTIPART_FORM_DATA);

        // This nested HttpEntiy is important to create the correct
        // Content-Disposition entry with metadata "name" and "filename"
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(filename)
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(someByteArray, fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);
        try {
             ResponseEntity<FileImportsResponse> response = restTemplate.exchange(
                    APIURL,
                    HttpMethod.POST,
                    requestEntity,
                     FileImportsResponse.class);
            return response.getBody().getFile();
//            ResponseEntity<FileImportsResponse> result = restTemplate.postForEntity(APIURL, requestEntity, FileImportsResponse.class);
//            return result.toString();
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method uses java.io.FileInputStream to read
     * file content into a byte array
     * @param file
     * @return byte[]
     */
    public byte[] readFileToByteArray(File file){
        FileInputStream fis = null;
        // Creating a byte array using the length of the file
        // file.length returns long which is cast to int
        byte[] bArray = new byte[(int) file.length()];
        try{
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();

        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return bArray;
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters =
                new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new FormHttpMessageConverter());
        return converters;
    }
}