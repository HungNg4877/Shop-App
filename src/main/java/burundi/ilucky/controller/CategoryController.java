package burundi.ilucky.controller;

import burundi.ilucky.payload.Request.CategoryRequest;
import burundi.ilucky.payload.Request.PagingRequest;
import burundi.ilucky.payload.Response.CategoryResponse;
import burundi.ilucky.payload.Response.PageResponse;
import burundi.ilucky.service.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.createCategory(request);
        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/all")
    public ResponseEntity<PageResponse<CategoryResponse>> getAllCategories(
            @RequestBody PagingRequest<CategoryRequest> pagingRequest) {

        PageResponse<CategoryResponse> response = categoryService.getAllCategories(pagingRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }
}
