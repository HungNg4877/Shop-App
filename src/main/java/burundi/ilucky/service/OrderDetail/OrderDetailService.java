package burundi.ilucky.service.OrderDetail;

import burundi.ilucky.model.Order;
import burundi.ilucky.model.OrderDetail;
import burundi.ilucky.model.Product;
import burundi.ilucky.payload.Request.OrderDetailRequest;
import burundi.ilucky.payload.Response.OrderDetailResponse;
import burundi.ilucky.repository.OrderDetailRepository;
import burundi.ilucky.repository.OrderRepository;
import burundi.ilucky.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Transactional
    public OrderDetailResponse createOrderDetail(OrderDetailRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }

        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(request.getQuantity())
                .totalMoney(request.getTotalMoney())
                .build();

        orderDetailRepository.save(orderDetail);

        return orderDetailMapper.mapToResponse(orderDetail);
    }
}
