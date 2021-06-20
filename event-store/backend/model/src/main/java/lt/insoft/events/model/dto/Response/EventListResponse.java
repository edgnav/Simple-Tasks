package lt.insoft.events.model.dto.Response;

import lombok.Getter;
import lombok.Setter;
import lt.insoft.events.model.dto.entity.EventListDto;

import java.util.List;

@Getter
@Setter
public class EventListResponse {
    private final List<EventListDto> eventListDto;
    private final Integer numberOfPages;

    public EventListResponse(List<EventListDto> eventListDto,Integer numberOfPages) {
        this.eventListDto = eventListDto;
        this.numberOfPages = numberOfPages;
    }
}