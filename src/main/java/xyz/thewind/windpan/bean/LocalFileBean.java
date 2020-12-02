package xyz.thewind.windpan.bean;

import java.io.Serializable;

public class LocalFileBean implements Serializable {
    private String fileId;
    private String fileName;
    private long uploadTime;
    private long fileSize;
    private String fileType;
    private String fileDesc;
    private String realPath;

    public LocalFileBean() {
    }

    public LocalFileBean(String fileId, String fileName, long uploadTime, long fileSize, String fileType, String fileDesc, String realPath) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.uploadTime = uploadTime;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.fileDesc = fileDesc;
        this.realPath = realPath;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }
}
