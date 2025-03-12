package burundi.ilucky.model;

import java.util.Date;
import java.util.Set;

import burundi.ilucky.model.dto.BaseEntity;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "_user")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private long totalPlay;

    private long totalVnd;

    private long totalStar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Order> orders;

}
