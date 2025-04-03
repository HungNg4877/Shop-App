package burundi.ilucky.repository;

import burundi.ilucky.model.Order;
import burundi.ilucky.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByOrder(Order order);
}
