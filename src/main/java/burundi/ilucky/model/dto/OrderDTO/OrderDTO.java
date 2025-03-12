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
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // PENDING, COMPLETED
    private BigDecimal totalAmount;
    private List<OrderItemDTO> items;
    private PaymentDTO payment;
}