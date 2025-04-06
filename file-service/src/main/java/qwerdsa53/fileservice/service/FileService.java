package qwerdsa53.fileservice.service;


import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(MultipartFile file) throws FileUploadException;

    void deleteFile(String filePath);

    void deleteFolder(String folderPath);
}
