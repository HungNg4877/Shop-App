package burundi.ilucky.service.Product;

import burundi.ilucky.model.Product;
import burundi.ilucky.payload.Response.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryName(product.getCategory().getName())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
