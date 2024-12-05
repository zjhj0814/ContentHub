package tibetyo.content_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tibetyo.content_hub.entity.Cast;

import java.util.Optional;

public interface CastRepository extends JpaRepository<Cast, Long> {
    Optional<Cast> findByName(String name);
}
