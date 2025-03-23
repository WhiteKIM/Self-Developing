package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.Category;
import whitekim.self_developing.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 정보 저장
     */
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    /**
     * 카테고리 목록 조회
     */
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    /**
     * 카테고리 검색 조회
     */
    public List<Category> searchCategory(String keyword) {
        return categoryRepository.findCategoriesByCategoryNameContaining(keyword);
    }

    /**
     * 카테고리 삭제
     */
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
