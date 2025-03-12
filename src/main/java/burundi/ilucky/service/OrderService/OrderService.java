package burundi.ilucky.service.OrderService;

import burundi.ilucky.Enum.OrderStatus;
import burundi.ilucky.Enum.PaymentStatus;
import burundi.ilucky.Exception.BaseException;
import burundi.ilucky.Patern.ErrorCode;
import burundi.ilucky.model.*;
import burundi.ilucky.model.dto.OrderDTO.OrderDTO;
import burundi.ilucky.payload.Request.OrderItemRequest;
import burundi.ilucky.payload.Request.OrderRequest;
import burundi.ilucky.repository.OrderRepository;
import burundi.ilucky.repository.PaymentRepository;
import burundi.ilucky.repository.ProductRepository;
import burundi.ilucky.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderMapper orderMapper;

    public OrderDTO createOrder(OrderRequest orderRequest) {
        // 1. Kiểm tra User có tồn tại không
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));

        // 2. Kiểm tra từng sản phẩm có đủ tồn kho không
        List<OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest item : orderRequest.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

            if (product.getStock() < item.getQuantity()) {
                throw new BaseException(ErrorCode.NO_MORE_PRODUCT);
            }

            // Trừ tồn kho
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            // Tạo OrderDetail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setPrice(product.getPrice());
            orderDetails.add(orderDetail);

            // Tính tổng tiền
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // 3. Tạo Order
        Order order = new Order();
        order.setUser(user);
        order.setOderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDetails(orderDetails);

        orderDetails.forEach(detail -> detail.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        // 4. Tạo Payment (nếu có)
        Payment payment = Payment.builder()
                .order(savedOrder)
                .amount(totalAmount)
                .paymentMethod(orderRequest.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .build();

        paymentRepository.save(payment);

        // 5. Trả về OrderDTO
        return orderMapper.mapToOrderDTO(savedOrder, payment);
    }


}

