package burundi.ilucky.repository;

import burundi.ilucky.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Optional<Token> findByRefreshToken(@Param("token") String refreshToken);
}
