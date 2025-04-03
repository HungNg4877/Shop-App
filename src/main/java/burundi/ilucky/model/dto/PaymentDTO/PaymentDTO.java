package burundi.ilucky.model.dto.PaymentDTO;

import burundi.ilucky.Enum.PaymentMethod;
import burundi.ilucky.Enum.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentDTO {
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // SUCCESS, PENDING
}
