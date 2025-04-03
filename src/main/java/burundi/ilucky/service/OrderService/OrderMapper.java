package burundi.ilucky.service.OrderService;

import burundi.ilucky.model.Order;
import burundi.ilucky.model.Payment;
import burundi.ilucky.model.dto.OrderDTO.OrderDTO;
import burundi.ilucky.model.dto.OrderDTO.OrderItemDTO;
import burundi.ilucky.model.dto.PaymentDTO.PaymentDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderMapper {
    public OrderDTO mapToOrderDTO(Order order, Payment payment) {
        List<OrderItemDTO> itemDTOs = order.getOrderDetails().stream()
                .map(detail -> OrderItemDTO.builder()
                        .productId(detail.getProduct().getId())
                        .productName(detail.getProduct().getName())
                        .quantity(detail.getQuantity())
                        .price(detail.getTotalMoney())
                        .build())
                .collect(Collectors.toList());

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .paymentMethod(payment.getPaymentMethod())
                .amount(payment.getAmount())
                .paymentStatus(payment.getStatus())
                .build();

        return OrderDTO.builder()
                .orderId(order.getId())
                .fullName(order.getFullName())
                .email(order.getEmail())
                .address(order.getAddress())
                .note(order.getNote())
                .shippingMethod(order.getShippingMethod())
                .shippingAddress(order.getAddress())
                .shippingDate(order.getOderDate())
                .orderDate(order.getOderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(itemDTOs)
                .payment(paymentDTO)
                .build();
    }
}
