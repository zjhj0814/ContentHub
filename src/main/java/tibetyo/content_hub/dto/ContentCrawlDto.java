package tibetyo.content_hub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tibetyo.content_hub.entity.ContentCategory;

import java.util.List;

@Data
@AllArgsConstructor
public class ContentCrawlDto {
    private String title;
    private ContentCategory category;
    private List<String> casts;
    private String description;
}
