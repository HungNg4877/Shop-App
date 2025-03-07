package burundi.ilucky.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "token")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private String accessToken;
    private String refreshToken;
    private boolean isRevoked;
}
