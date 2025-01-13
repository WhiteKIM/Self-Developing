package whitekim.self_developing.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import whitekim.self_developing.model.Image;
import whitekim.self_developing.repository.ImageRepository;
import whitekim.self_developing.utils.FileUtils;

import java.util.Optional;

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

    public void saveImage(MultipartFile uploadFile) {
        String uploadFileName = fileUtils.uploadFile(uploadFile);
        int index = uploadFileName.lastIndexOf("/");
        Image image = new Image(uploadFileName, uploadFileName.substring(index), uploadFile.getOriginalFilename());

        imageRepository.save(image);
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
