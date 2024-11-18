package tibetyo.content_hub;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CastRepository extends JpaRepository<Cast, Long> {
    Optional<Cast> findByName(String name);
}
