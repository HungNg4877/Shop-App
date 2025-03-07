package burundi.ilucky.payload.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Response {
	public String status;
	public String message;
}
