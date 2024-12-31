package tibetyo.content_hub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tibetyo.content_hub.dto.AvailabilityCreateRequestDto;
import tibetyo.content_hub.dto.AvailabilityResponseDto;
import tibetyo.content_hub.dto.AvailabilityUpdateDto;
import tibetyo.content_hub.entity.Availability;
import tibetyo.content_hub.entity.Content;
import tibetyo.content_hub.entity.ContentStatus;
import tibetyo.content_hub.entity.Ott;
import tibetyo.content_hub.exception.CustomException;
import tibetyo.content_hub.exception.ErrorCode;
import tibetyo.content_hub.repository.AvailabilityRepository;
import tibetyo.content_hub.repository.ContentRepository;
import tibetyo.content_hub.repository.OttRepository;

import java.util.List;

@Service
@Transactional(readOnly=true)
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final ContentRepository contentRepository;
    private final OttRepository ottRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository, ContentRepository contentRepository, OttRepository ottRepository) {
        this.availabilityRepository = availabilityRepository;
        this.contentRepository = contentRepository;
        this.ottRepository = ottRepository;
    }

    @Transactional
    public void saveAvailability(AvailabilityCreateRequestDto availabilityCreateRequestDto) {
        Content content = contentRepository.findById(availabilityCreateRequestDto.getContentId())
                .orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        Ott ott = ottRepository.findByName(availabilityCreateRequestDto.getOttName())
                .orElseThrow(() -> new CustomException(ErrorCode.OTT_NOT_FOUND));
        Availability availability = Availability.builder().content(content).ott(ott).contentStatus(availabilityCreateRequestDto.getStatus()).build();
        availabilityRepository.save(availability);
    }

    @Transactional
    public void updateAvailability(AvailabilityUpdateDto availabilityUpdateDto){
        Availability availability = availabilityRepository.findById(availabilityUpdateDto.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.AVAILABILITY_NOT_FOUND));
        availability.updateStatus(availabilityUpdateDto.getStatus());
    }

    public List<AvailabilityResponseDto> findAvailabilitiesByContentId(Long contentId){
        return availabilityRepository.findAvailabilitiesByContentId(contentId);
    }

    public List<AvailabilityResponseDto> findAvailabilitiesByOttId(Long ottId, List<ContentStatus> statuses){
        return availabilityRepository.findAvailabilitiesByOttId(ottId, statuses);
    }
}