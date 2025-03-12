package burundi.ilucky.service.Product;

import burundi.ilucky.Exception.BaseException;
import burundi.ilucky.Patern.ErrorCode;
import burundi.ilucky.model.Category;
import burundi.ilucky.model.Product;
import burundi.ilucky.payload.Request.ProductRequest;
import burundi.ilucky.payload.Response.ProductResponse;
import burundi.ilucky.repository.CategoryRepository;
import burundi.ilucky.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductMapper productMapper;

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        // 1. Tìm Category theo ID
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new BaseException(ErrorCode.CATEGORY_NOT_FOUND));

        // 2. Tạo đối tượng Product từ request
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();

        // 3. Lưu Product vào database
        Product savedProduct = productRepository.save(product);

        // 4. Trả về DTO
        return productMapper.mapToResponse(savedProduct);
    }

}