package burundi.ilucky.service.Category;

import burundi.ilucky.model.Category;
import burundi.ilucky.payload.Response.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .isDelete(category.isDelete())
                .build();
    }
}
