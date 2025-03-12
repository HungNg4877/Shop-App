package burundi.ilucky.model;

import burundi.ilucky.model.dto.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product extends BaseEntity {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    @ManyToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
