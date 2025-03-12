package burundi.ilucky.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import burundi.ilucky.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
    private Long id;
    private String phone;
    private LocalDateTime addTime;
    private long totalPlay;
    private long totalStar;
    
//    @JsonProperty("isPremium")
    private boolean isPremium;
    
    @JsonProperty("isWin")
    private boolean isWin;
    
    
    public UserDTO(User user) {
        this.id = user.getId();
        this.addTime = user.getCreatedAt();
        this.totalPlay = user.getTotalPlay();
        this.totalStar = user.getTotalStar();
    }


}
