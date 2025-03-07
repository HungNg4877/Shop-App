package burundi.ilucky.payload.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingRequest<T> {
    private int page;
    private int size;
    private String sortField;
    private Sort.Direction sortBy;
    private T filter;
}