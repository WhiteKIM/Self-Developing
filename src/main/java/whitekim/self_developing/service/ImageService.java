package whitekim.self_developing.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import whitekim.self_developing.model.Image;
import whitekim.self_developing.repository.ImageRepository;
import whitekim.self_developing.utils.FileUtils;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final FileUtils fileUtils;

    public Image getImage(Long id, HttpServletResponse response) {
        Optional<Image> optionalImage = imageRepository.findById(id);

        if(optionalImage.isEmpty())
            throw new RuntimeException("표시할 이미지가 없습니다.");

        Image image = optionalImage.get();
        fileUtils.getUploadFile(image.getFilename(), response);

        return image;
    }

    public Resource getImage(String filename) {
        Optional<Image> optFile = imageRepository.findByFilename(filename);

        if(optFile.isEmpty())
            return null;

        Image image = optFile.get();

        return fileUtils.getUploadFile(image.getFilepath());
    }

    public Image saveImage(MultipartFile uploadFile) {
        String uploadFileName = fileUtils.uploadFile(uploadFile);
        int index = uploadFileName.lastIndexOf("/");

        log.info("[ImageService] : {}", uploadFileName);

        Image image = new Image(uploadFileName, uploadFileName.substring(index+1), uploadFile.getOriginalFilename());

        return imageRepository.save(image);
    }

    public void updateImage(Long id, MultipartFile uploadFile) {

    }

    public void deleteImage(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);

        if(optionalImage.isEmpty())
            throw new RuntimeException("삭제할 이미지가 없습니다.");

        fileUtils.deleteUploadFile(optionalImage.get().getFilename());
        imageRepository.deleteById(id);
    }


}
