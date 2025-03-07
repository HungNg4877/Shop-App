package burundi.ilucky.payload.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseResponse {
    private String message;
    private long totalPlay;
    private long totalVnd;
}
