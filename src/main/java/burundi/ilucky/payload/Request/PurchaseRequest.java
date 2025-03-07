package burundi.ilucky.payload.Request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PurchaseRequest {
    @Min(value = 1, message = "Số lượng lượt chơi phải lớn hơn 0.")
    private long quantity;
}
