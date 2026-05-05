package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import whitekim.self_developing.model.Image;
import whitekim.self_developing.service.ImageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;
    private final String imageUrl = "http://127.0.0.1:8080/api/image/";

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImageFile(@PathVariable String filename) {
        Resource resource = imageService.getImage(filename);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(MultipartFile uploadImage) throws HttpMediaTypeNotSupportedException {
        if(!uploadImage.getContentType().startsWith("image")) {
            throw new HttpMediaTypeNotSupportedException("업로드를 지원하지 않는 항목입니다.");
        }

        Image saveImage = imageService.saveImage(uploadImage);

        return ResponseEntity.ok(imageUrl + saveImage.getFilename());
    }
}
