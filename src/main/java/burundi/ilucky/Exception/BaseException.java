package burundi.ilucky.Exception;

import burundi.ilucky.Patern.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException{
    private ErrorCode errorCode;
}

