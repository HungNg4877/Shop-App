package burundi.ilucky.model.dto.OrderDTO;

import burundi.ilucky.Enum.OrderStatus;
import burundi.ilucky.model.dto.PaymentDTO.PaymentDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDTO {
    private Long orderId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String note;
    private BigDecimal totalAmount;
    private String shippingMethod;
    private String shippingAddress;
    private LocalDateTime shippingDate;
    private LocalDateTime orderDate;
    private OrderStatus status; // PENDING, COMPLETED
    private List<OrderItemDTO> items;
    private PaymentDTO payment;
}