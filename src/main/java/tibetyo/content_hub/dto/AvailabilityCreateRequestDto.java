package tibetyo.content_hub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tibetyo.content_hub.entity.ContentStatus;

@Getter
@AllArgsConstructor
public class AvailabilityCreateRequestDto {
    private Long contentId;
    private String ottName;
    private ContentStatus status;
}
