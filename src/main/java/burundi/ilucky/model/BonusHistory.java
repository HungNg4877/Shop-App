package burundi.ilucky.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class BonusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    private int bonusAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date bonusTime;
}
