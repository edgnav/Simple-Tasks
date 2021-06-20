package lt.insoft.events.app.events;

import lombok.RequiredArgsConstructor;
import lt.insoft.events.app.events.entity.EventEntity;
import lt.insoft.events.app.events.service.EventService;
import lt.insoft.events.app.tag.entity.TagEntity;
import lt.insoft.events.model.dto.Response.EventListResponse;
import lt.insoft.events.model.dto.entity.EventDto;
import lt.insoft.events.model.dto.entity.EventListDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public ResponseEntity<List<EventEntity>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<EventDto> getAll(@PathVariable long id) {
        EventDto dto = eventService.findById(id);
        if (dto == null) {
            ResponseEntity.badRequest().body("Failed to find event");
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/event")
    public ResponseEntity<EventEntity> createEvent(@RequestBody EventDto eventDto, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        return ResponseEntity.ok(eventService.addEvent(eventDto, userDetails.getUsername()));
    }

    @PostMapping("event/join/{eventId}")
    public EventDto addParticipant(@PathVariable long eventId, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        return eventService.addParticipant(eventId, userDetails.getUsername());
    }

    @GetMapping("/eventListTest")
    public ResponseEntity<List<EventEntity>> getEventListTest() {
        return ResponseEntity.ok(eventService.testGetEvents());
    }

    @PostMapping("/tagtest")
    public ResponseEntity<TagEntity> addTag(@RequestBody String[] tag) {
        return ResponseEntity.ok(eventService.addTag(tag[0]));
    }

    @GetMapping("/event/search")
    public ResponseEntity<EventListResponse> searchEventsList(@RequestParam String search, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(eventService.searchEventsList(search, PageRequest.of(page, size, Sort.unsorted())));
    }

    @PostMapping("/event/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto eventDto,
                                                @AuthenticationPrincipal UserDetails userDetails,
                                                @PathVariable long eventId) throws Exception {
        return ResponseEntity.ok(eventService.updateEvent(eventDto, userDetails.getUsername(), eventId));
    }

    @PostMapping("/event/leave/{eventId}")
    public ResponseEntity<Boolean> leaveEvent(@PathVariable long eventId, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(eventService.leaveEvent(eventId, userDetails.getUsername()));
    }
}
