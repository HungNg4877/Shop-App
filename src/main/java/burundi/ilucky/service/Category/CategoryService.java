package burundi.ilucky.service.Category;

import burundi.ilucky.Exception.BaseException;
import burundi.ilucky.Patern.ErrorCode;
import burundi.ilucky.Util.PageUtil;
import burundi.ilucky.model.Category;
import burundi.ilucky.payload.Request.CategoryRequest;
import burundi.ilucky.payload.Request.PagingRequest;
import burundi.ilucky.payload.Response.CategoryResponse;
import burundi.ilucky.payload.Response.PageResponse;
import burundi.ilucky.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {

        if (categoryRepository.existsByName(request.getName())) {
            throw new BaseException(ErrorCode.CATEGORY_EXIST);
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.mapToResponse(savedCategory);
    }
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
        return categoryMapper.mapToResponse(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setDelete(true);
        categoryRepository.save(category);
    }

    public PageResponse<CategoryResponse> getAllCategories(PagingRequest<CategoryRequest> pagingRequest) {
        PageRequest pageRequest = PageUtil.getPageRequest(pagingRequest);
        Page<Category> page = categoryRepository.findAll(pageRequest);
        List<CategoryResponse> responsePage = new ArrayList<>();
        for (Category category : page.getContent()){
            CategoryResponse categoryResponse = categoryMapper.mapToResponse(category);
            responsePage.add(categoryResponse);
        }
        return new PageResponse<>(new PageImpl<>(responsePage, pageRequest, page.getTotalElements()));
    }
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return categoryMapper.mapToResponse(category);
    }


}
