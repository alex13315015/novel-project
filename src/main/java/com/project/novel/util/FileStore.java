//package com.project.novel.util;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.UUID;
//
//@Component
//public class FileStore {
//
//    @Value("${file.dir}")
//    private String fileDir;
//
//    public String getFilePath(String filename){
//        return fileDir + filename;
//    }
//
//    public String storeFile(MultipartFile multipartFile) throws IOException {
//        if(multipartFile.isEmpty()){
//            return null;
//        }
//
//        String originalFileName = multipartFile.getOriginalFilename();
//        String storeFileName = createStoreFileName(originalFileName);
//        multipartFile.transferTo(new File(getFilePath(storeFileName)));
//
//        return storeFileName;
//    }
//
//    private String createStoreFileName(String originalFileName) {
//        String uuid = UUID.randomUUID().toString();
//        String ext = extractExt(originalFileName);
//        return uuid + "." + ext;
//    }
//
//    private String extractExt(String originalFileName) {
//        int pos = originalFileName.lastIndexOf(".");
//        return originalFileName.substring(pos + 1);
//    }
//}
