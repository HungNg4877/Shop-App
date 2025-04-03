package burundi.ilucky.payload.Request;

import burundi.ilucky.Patern.Mess;
import burundi.ilucky.Patern.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {
    @NotBlank
    private String userName;

    @Pattern(
            regexp = Regex.PASSWORD_REGEX,
            message = Mess.PASSWORD
    )
    private String passWord;

}
