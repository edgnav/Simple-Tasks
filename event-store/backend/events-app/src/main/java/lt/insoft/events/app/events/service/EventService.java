package lt.insoft.events.app.events.service;

import lombok.RequiredArgsConstructor;
import lt.insoft.events.app.events.entity.EventEntity;
import lt.insoft.events.app.events.repository.EventRepo;
import lt.insoft.events.app.events.repository.search.EventSearch;
import lt.insoft.events.app.tag.entity.TagEntity;
import lt.insoft.events.app.tag.repository.TagRepo;
import lt.insoft.events.app.user.entity.UserEntity;
import lt.insoft.events.app.user.repository.UserRepo;
import lt.insoft.events.model.dto.Response.EventListResponse;
import lt.insoft.events.model.dto.entity.EventDto;
import lt.insoft.events.model.dto.entity.EventListDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepo eventRepo;
    private final UserRepo userRepository;
    private final TagRepo tagRepository;

    public List<EventEntity> getAll() {
        return eventRepo.findAll();
    }

    public EventEntity addEvent(EventDto dto, String creator) throws Exception {
        EventEntity event = new EventEntity();
        Optional<UserEntity> user = userRepository.findByEmail(creator);
        if (user.isEmpty()) {
            throw new Exception();
        }

        event.setCreator(user.get());
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setDateFrom(dto.getDateFrom());
        event.setDateTo(dto.getDateTo());
        iterateThroughTags(dto, event);
        return eventRepo.save(event);
    }

    public EventDto addParticipant(long eventId, String email) throws Exception {
        try {
            EventEntity event = eventRepo.findById(eventId).get();
            Optional<UserEntity> user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                throw new Exception();
            }
            event.getUserList().add(user.get());
            EventEntity insertedEvent = eventRepo.save(event);

            EventDto dto = new EventDto();
            dto.setDescription(insertedEvent.getDescription());
            dto.setName(insertedEvent.getName());
            dto.setDateTo(insertedEvent.getDateTo());
            dto.setDateFrom(insertedEvent.getDateFrom());

            return getEventDto(insertedEvent, dto);

        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @NotNull
    private EventDto getEventDto(EventEntity insertedEvent, EventDto dto) {
        Set<String> emails = new HashSet<>();
        insertedEvent.getUserList().forEach(u -> emails.add(u.getEmail()));
        dto.setUserList(emails);

        Set<String> tags = new HashSet<>();
        insertedEvent.getEventTag().forEach(u -> tags.add(u.getTag()));
        dto.setTags(tags);
        return dto;
    }

    public EventDto findById(long id) {
        EventEntity event = eventRepo.findById(id).get();
        if (event == null) {
            return null;
        }
        EventDto dto = new EventDto();
        dto.setDescription(event.getDescription());
        dto.setName(event.getName());
        dto.setDateTo(event.getDateTo());
        dto.setDateFrom(event.getDateFrom());
        dto.setCreator(event.getCreator().getEmail());
        return getEventDto(event, dto);
    }

    public List<EventEntity> testGetEvents() {
        return eventRepo.findAll();
    }

    public TagEntity addTag(String tag) {
        TagEntity entity = new TagEntity();
        entity.setTag(tag);
        entity.setId(100L);
        return tagRepository.save(entity);
    }

    public EventListResponse searchEventsList(String searchCriteria, PageRequest pageRequest) {
        EventSearch search = new EventSearch(searchCriteria, searchCriteria);
        return eventRepo.searchEventsList(search, pageRequest);
    }

    public EventDto updateEvent(EventDto dto, String creator, long eventId) throws Exception {

        EventEntity event = eventRepo.findById(eventId).get();

        if (!event.getCreator().getEmail().equals(creator)) {
            throw new Exception("Wrong creator");
        }
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setDateFrom(dto.getDateFrom());
        event.setDateTo(dto.getDateTo());
        Set<TagEntity> resetTagList = new HashSet<>();
        event.setEventTag(resetTagList);

        iterateThroughTags(dto, event);
        eventRepo.save(event);
        return dto;
    }

    private void iterateThroughTags(EventDto dto, EventEntity event) {
        for (String tag : dto.getTags()) {
            Optional<TagEntity> foundTag = tagRepository.getTagByName(tag);
            if (foundTag.isPresent()) {
                event.getEventTag().add(foundTag.get());
            } else {
                TagEntity tagToInsert = new TagEntity();
                tagToInsert.setTag(tag);
                TagEntity insertedTag = tagRepository.save(tagToInsert);
                event.getEventTag().add(insertedTag);
            }
        }
    }

    public boolean leaveEvent(long eventId, String email) {
        Optional<EventEntity> event = eventRepo.findById(eventId);
        if (event.isEmpty()) {
            return false;
        }
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        event.get().getUserList().remove(user.get());
        eventRepo.save(event.get());
        return true;
    }
}

