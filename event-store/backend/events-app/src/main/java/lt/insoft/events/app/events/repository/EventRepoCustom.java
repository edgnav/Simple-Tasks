package lt.insoft.events.app.events.repository;

import lt.insoft.events.app.events.entity.EventEntity;
import lt.insoft.events.app.events.repository.search.EventSearch;
import lt.insoft.events.model.dto.Response.EventListResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface EventRepoCustom extends JpaRepository<EventEntity, Long> {

    EventListResponse searchEventsList(EventSearch search, PageRequest pageRequest);
}
