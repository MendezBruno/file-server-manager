package com.common.uploadback;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("fileImport")
public class FileImports {
    @Id
    private ObjectId id;

    private String fieldName;

    private String originalName;

    private String encoding;

    private String mimeType;

    private String destination;

    private String filename;

    private String path;

    private int size;

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return this.id;
    }


    public void setFieldname(String fieldname) {
        this.fieldName = fieldname;
    }

    public String getFieldname() {
        return this.fieldName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }


}

