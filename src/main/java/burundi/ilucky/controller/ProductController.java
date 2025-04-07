package burundi.ilucky.controller;

import burundi.ilucky.Util.PageUtil;
import burundi.ilucky.model.dto.OrderDTO.OrderDTO;
import burundi.ilucky.payload.Request.OrderRequest;
import burundi.ilucky.payload.Request.PagingRequest;
import burundi.ilucky.payload.Request.ProductRequest;
import burundi.ilucky.payload.Response.PageResponse;
import burundi.ilucky.payload.Response.ProductResponse;
import burundi.ilucky.service.Category.CategoryIdFilter;
import burundi.ilucky.service.Product.ProductRedisService;
import burundi.ilucky.service.Product.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRedisService productRedisService;
    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }
    // Update Product
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {
        ProductResponse updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
    @PostMapping("/all")
    public ResponseEntity<PageResponse<ProductResponse>> getAllProducts(@RequestParam(required = false, defaultValue = "") String keyword,
            @RequestBody PagingRequest<CategoryIdFilter> pagingRequest) {
        PageRequest pageRequest = PageUtil.getPageRequest(pagingRequest);
        String rawCategoryId = (pagingRequest.getFilter() != null) ? pagingRequest.getFilter().getIdCategory() : null;
        Long categoryId = null;
        if (rawCategoryId != null && !"all".equalsIgnoreCase(rawCategoryId) && !rawCategoryId.isBlank()) {
            try {
                categoryId = Long.parseLong(rawCategoryId);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        // Kiểm tra dữ liệu trong Redis cache
        List<ProductResponse> cachedProducts = productRedisService.getAllProducts(keyword, categoryId, pageRequest);
        if (cachedProducts != null) {
            PageResponse<ProductResponse> cachedResponse = new PageResponse<>(
                    new PageImpl<>(cachedProducts, pageRequest, cachedProducts.size())
            );
            return ResponseEntity.ok(cachedResponse);
        }

        // Nếu không có cache, lấy từ DB
        PageResponse<ProductResponse> response = productService.getAllProducts(keyword, pagingRequest);

        // Lưu vào Redis cache
        try {
            productRedisService.saveAllProducts(response.getContent(), keyword, categoryId, pageRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse productResponse = productService.getProductById(id);
        return ResponseEntity.ok(productResponse);
    }

}
