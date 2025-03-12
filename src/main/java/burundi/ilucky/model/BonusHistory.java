package burundi.ilucky.model;

import burundi.ilucky.model.dto.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class BonusHistory extends BaseEntity {

    @ManyToOne
    private User user;

    private int bonusAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date bonusTime;
}
