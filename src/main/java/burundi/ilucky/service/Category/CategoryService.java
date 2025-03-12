package burundi.ilucky.service.Category;

import burundi.ilucky.Exception.BaseException;
import burundi.ilucky.Patern.ErrorCode;
import burundi.ilucky.model.Category;
import burundi.ilucky.payload.Request.CategoryRequest;
import burundi.ilucky.payload.Response.CategoryResponse;
import burundi.ilucky.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        // 1. Kiểm tra danh mục đã tồn tại chưa
        if (categoryRepository.existsByName(request.getName())) {
            throw new BaseException(ErrorCode.CATEGORY_EXIST);
        }

        // 2. Tạo đối tượng Category từ request
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        // 3. Lưu Category vào database
        Category savedCategory = categoryRepository.save(category);

        // 4. Trả về CategoryResponse
        return categoryMapper.mapToResponse(savedCategory);
    }

}
