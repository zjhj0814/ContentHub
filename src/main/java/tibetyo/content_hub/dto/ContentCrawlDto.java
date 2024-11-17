package tibetyo.content_hub.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import tibetyo.content_hub.content.ContentCategory;

@Data
@AllArgsConstructor
public class ContentCrawlDto {
    private String title;
    private ContentCategory category;
    private List<String> casts;
    private String description;
}
