package whitekim.self_developing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whitekim.self_developing.dto.request.PageForm;
import whitekim.self_developing.dto.response.DetailPageForm;
import whitekim.self_developing.exception.NotExistDataTypeException;
import whitekim.self_developing.exception.NotFoundDataException;
import whitekim.self_developing.model.Category;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.Page;
import whitekim.self_developing.model.type.PageType;
import whitekim.self_developing.repository.CategoryRepository;
import whitekim.self_developing.repository.CertRepository;
import whitekim.self_developing.repository.PageRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PageService {
    private final PageRepository pageRepository;
    private final CategoryRepository categoryRepository;
    private final CertRepository certRepository;

    public void registerPage(PageForm pageForm) {
        Optional<Category> optCategory = categoryRepository.findByCategoryName(pageForm.getCategory());
        Optional<Certification> optCert = certRepository.findByCertName(pageForm.getCertification());

        if(optCategory.isEmpty() || optCert.isEmpty()) {
            throw new NotExistDataTypeException("존재하지 않는 항목입니다.");
        }

        Page page = Page.builder()
                .pageType(PageType.valueOf(pageForm.getPageType()))
                .certification(optCert.get())
                .category(optCategory.get())
                .build();

        pageRepository.save(page);
    }

    public DetailPageForm getDetailPage(Long id) {
        Optional<Page> optPage = pageRepository.findById(id);

        if(optPage.isEmpty())
            throw new NotFoundDataException("해당 정보를 찾을 수 없습니다.");

        return new DetailPageForm(optPage.get());
    }

    public List<Page> getPageList() {
        return pageRepository.findAll();
    }

    public void deletePage(Long id) {
        pageRepository.deleteById(id);
    }
}
