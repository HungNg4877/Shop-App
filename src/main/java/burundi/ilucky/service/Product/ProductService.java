package burundi.ilucky.service.Product;

import burundi.ilucky.Exception.BaseException;
import burundi.ilucky.Patern.ErrorCode;
import burundi.ilucky.Util.PageUtil;
import burundi.ilucky.model.Category;
import burundi.ilucky.model.Product;
import burundi.ilucky.payload.Request.PagingRequest;
import burundi.ilucky.payload.Request.ProductRequest;
import burundi.ilucky.payload.Response.PageResponse;
import burundi.ilucky.payload.Response.ProductResponse;
import burundi.ilucky.repository.CategoryRepository;
import burundi.ilucky.repository.ProductRepository;
import burundi.ilucky.service.Category.CategoryIdFilter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    // Update Product
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Cập nhật thông tin sản phẩm
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());


        Product updatedProduct = productRepository.save(product);
        return productMapper.mapToResponse(updatedProduct);
    }

    // Delete Product
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setDelete(product.isDelete());
        productRepository.delete(product);
    }
    public PageResponse<ProductResponse> getAllProducts(String keyword, PagingRequest<CategoryIdFilter> pagingRequest) {
        PageRequest pageRequest = PageUtil.getPageRequest(pagingRequest);
        Long categoryId = pagingRequest.getFilter() != null ? pagingRequest.getFilter().getIdCategory() : null;
        Page<Product> productPage = productRepository.searchProducts(categoryId, keyword, pageRequest);
        List<ProductResponse> productResponses = productPage.getContent().stream()
                .map(productMapper::mapToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(new PageImpl<>(productResponses, pageRequest, productPage.getTotalElements()));
    }
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        return productMapper.mapToResponse(product);
    }

}