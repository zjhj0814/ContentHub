package tibetyo.content_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tibetyo.content_hub.entity.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
