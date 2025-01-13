package whitekim.self_developing.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class FileUtils {

    @Value("${path.upload.windows.path}")
    private String windowsPath;

    @Value("${path.upload.linux.path}")
    private String linuxPath;

    public String uploadFile(MultipartFile uploadFile) {
        String filename = uploadFile.getOriginalFilename();
        LocalDate today = LocalDate.now();

        if(uploadFile.isEmpty())
            return null;

        // 저장될 경로
        String path = getUploadPathByEnvironment() + "/" + today.toString();

        File uploadPath = new File(path);
        if(!uploadPath.isDirectory()) {
            uploadPath.mkdirs();
        }

        int prefixIndex = filename.lastIndexOf(".");
        String prefix = filename.substring(prefixIndex);

        String newFileName = UUID.randomUUID().toString()+"."+prefix;
        String filePath = path + "/"+newFileName;
        File upload = new File(filePath);
        try {
            uploadFile.transferTo(upload);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드에 실패하였습니다.");
        }

        return newFileName;
    }

    public void getUploadFile(String filePath, HttpServletResponse response) {
        File file = new File(filePath);
        response.setContentType("application/download");//콘텐츠 타입 지정
        response.setContentLength((int)file.length());//파일 크기 지정
        response.setHeader("Content-disposition", "attachment;filename=\""+file.getName()+"\"");//저장된 이름이 아닌 파일 원본명

        try (OutputStream os = response.getOutputStream(); FileInputStream fs = new FileInputStream(file);)  {
            FileCopyUtils.copy(fs, os);//Response에 담아보내기
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 기존에 업로드된 파일을 제거하고, 업로드된 파일로 대체
     * @param fileName - 기존에 업로드한 파일명
     * @param uploadFile - 새롭게 업로드한 파일
     */
    public void updateUploadFile(String fileName, MultipartFile uploadFile) {

    }

    /**
     * 물리적으로도 파일을 제거한다.
     * @param fileName - 제거할 파일명
     */
    public void deleteUploadFile(String fileName) {

    }

    /**
     * 시스템 환경에 맞는 경로를 생성하고 반환하는 로직
     * @return path : 시스템별 업로드 경로
     */
    private String getUploadPathByEnvironment() {
        String os = System.getProperty("os.name");
        String path = "";

        if(os.contains("win")) {
            path = windowsPath;
        } else if(os.contains("nux")) {
            path = linuxPath;
        }

        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("파입업로드 경로를 생성하지 못하였습니다.");
        }

        return path;
    }
}
