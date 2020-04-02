package com.tubes.kouveepetshop.Model;

public class FileProductDAO {
    private String fileName, fileSize, status;

    public FileProductDAO(String fileName, String fileSize, String status) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getStatus() {
        return status;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
