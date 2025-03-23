package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whitekim.self_developing.model.Category;
import whitekim.self_developing.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * 전체 카테고리 리스트를 반환
     * @return
     */
    @GetMapping("/v1/list")
    public ResponseEntity<List<Category>> getCategoryList() {
        List<Category> categoryList = categoryService.getCategoryList();

        return ResponseEntity.ok(categoryList);
    }

    /**
     * 키워드를 이용해서 카테고리 목록 검색 조회
     * @param keyword - 검색 키워드
     * @return
     */
    @GetMapping("/v1/search")
    public ResponseEntity<List<Category>> searchCategory(@RequestParam String keyword) {
        List<Category> categoryList = categoryService.searchCategory(keyword);

        return ResponseEntity.ok(categoryList);
    }

    @PostMapping("/v1/save")
    public ResponseEntity<String> saveCategory(@RequestBody Category category) {
        categoryService.saveCategory(category);

        return ResponseEntity.ok("Save Success");
    }

    @DeleteMapping("/v1/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam Long id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.ok("delete Success");
    }
}
