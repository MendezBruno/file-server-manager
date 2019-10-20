package com.common.uploadback;


public class FileStorageProperties {
    private String uploadDir;

    public FileStorageProperties() {

        this.setUploadDir("/uploads");
    }

    public FileStorageProperties(String customFolder) {
        this.setUploadDir("/"+customFolder);
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
