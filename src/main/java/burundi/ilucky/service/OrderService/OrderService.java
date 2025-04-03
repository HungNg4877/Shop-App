package burundi.ilucky.service.OrderService;

import burundi.ilucky.Enum.OrderStatus;
import burundi.ilucky.Enum.PaymentMethod;
import burundi.ilucky.Enum.PaymentStatus;
import burundi.ilucky.Exception.BaseException;
import burundi.ilucky.Patern.ErrorCode;
import burundi.ilucky.model.*;
import burundi.ilucky.model.dto.OrderDTO.OrderDTO;
import burundi.ilucky.payload.Request.OrderItemRequest;
import burundi.ilucky.payload.Request.OrderRequest;
import burundi.ilucky.payload.Response.OrderDetailResponse;
import burundi.ilucky.payload.Response.OrderResponse;
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
import java.util.stream.Collectors;

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
            orderDetail.setTotalMoney(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderDetails.add(orderDetail);

            // Tính tổng tiền
            totalAmount = totalAmount.add(orderDetail.getTotalMoney());
        }

        PaymentMethod paymentMethod = orderRequest.getPaymentMethod();

        // 3. Tạo Order
        Order order = new Order();
        order.setUser(user);
        order.setFullName(orderRequest.getFullName());
        order.setEmail(orderRequest.getEmail());
        order.setPhoneNumber(orderRequest.getPhoneNumber());
        order.setAddress(orderRequest.getAddress());
        order.setNote(orderRequest.getNote());
        order.setTotalAmount(totalAmount);
        order.setShippingMethod(orderRequest.getShippingMethod());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setShippingDate(orderRequest.getShippingDate());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setOderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDetails(orderDetails);

        orderDetails.forEach(detail -> detail.setOrder(order));

        Order savedOrder = orderRepository.save(order);

        // 4. Tạo Payment (nếu có)
        Payment payment = createPayment(savedOrder,paymentMethod, totalAmount);

        // 5. Trả về OrderDTO
        return orderMapper.mapToOrderDTO(savedOrder, payment);
    }
    private Payment createPayment(Order order, PaymentMethod paymentMethod, BigDecimal amount) {
        if (paymentMethod == null) return null;

        Payment payment = Payment.builder()
                .order(order)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

//    public OrderDTO updateOrder(Long orderId, OrderRequest request) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
//
//        // Cập nhật thông tin đơn hàng
//        order.setFullName(request.getFullName());
//        order.setEmail(request.getEmail());
//        order.setPhoneNumber(request.getPhoneNumber());
//        order.setAddress(request.getAddress());
//        order.setNote(request.getNote());
//        order.setShippingMethod(request.getShippingMethod());
//        order.setShippingAddress(request.getShippingAddress());
//        order.setShippingDate(request.getShippingDate());
//        order.setPaymentMethod(request.getPaymentMethod());
//
//        // Lưu lại
//        Order updatedOrder = orderRepository.save(order);
//        return orderMapper.mapToOrderDTO(updatedOrder);
//    }

    // Xóa đơn hàng
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }

//    public OrderResponse getOrderById(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        OrderResponse response = new OrderResponse();
//        response.setId(order.getId());
//        response.setFullName(order.getFullName());
//        response.setEmail(order.getEmail());
//        response.setPhoneNumber(order.getPhoneNumber());
//        response.setAddress(order.getAddress());
//        response.setTotalAmount(order.getTotalAmount());
//        response.setStatus(order.getStatus().name());
//        response.setOrderDate(order.getOderDate());
//
//        // Chuyển danh sách OrderDetail -> OrderDetailResponse
//        response.setOrderDetails(order.getOrderDetails().stream().map(detail -> {
//            OrderDetailResponse detailResponse = new OrderDetailResponse();
//            detailResponse.setProductId(detail.getProduct().getId());
//            detailResponse.setQuantity(detail.getQuantity());
//            detailResponse.setTotalMoney(detail.getTotalMoney());
//            return detailResponse;
//        }).collect(Collectors.toList()));
//
//        return response;
//    }
}

