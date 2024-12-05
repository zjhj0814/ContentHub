package tibetyo.content_hub.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tibetyo.content_hub.dto.ContentCrawlDto;
import tibetyo.content_hub.entity.Cast;
import tibetyo.content_hub.entity.Content;
import tibetyo.content_hub.entity.ContentCast;
import tibetyo.content_hub.repository.CastRepository;
import tibetyo.content_hub.repository.ContentCastRepository;
import tibetyo.content_hub.repository.ContentRepository;

@Service
public class ContentCastService {
    private final ContentRepository contentRepository;
    private final CastRepository castRepository;
    private final ContentCastRepository contentCastRepository;

    public ContentCastService(ContentRepository contentRepository, CastRepository castRepository,
                              ContentCastRepository contentCastRepository) {
        this.contentRepository = contentRepository;
        this.castRepository = castRepository;
        this.contentCastRepository = contentCastRepository;
    }

    @Transactional
    public void saveCrawlingData(ContentCrawlDto contentCrawlDto) {
        Content content = contentRepository.save(
                Content.builder()
                        .title(contentCrawlDto.getTitle())
                        .description(contentCrawlDto.getDescription())
                        .category(contentCrawlDto.getCategory())
                        .build()
        );

        for (String castName : contentCrawlDto.getCasts()) {
            Cast cast = castRepository.findByName(castName)
                    .orElseGet(() -> castRepository.save(
                            Cast.builder()
                                    .name(castName)
                                    .build())
                    );

            ContentCast contentCast = new ContentCast();
            content.addContentCast(contentCast);
            cast.addContentCast(contentCast);
            contentCastRepository.save(contentCast);
        }
    }
}
