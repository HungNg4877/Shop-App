package burundi.ilucky.repository;

import burundi.ilucky.model.BonusHistory;
import burundi.ilucky.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BonusHistoryRepository extends JpaRepository<BonusHistory, Long> {
    List<BonusHistory> findByUser(User user); // Lấy lịch sử theo user
}
