package lt.insoft.events.app.events.repository;

import lt.insoft.events.app.events.entity.EventEntity;
import lt.insoft.events.app.events.entity.EventEntity_;
import lt.insoft.events.app.events.repository.search.EventSearch;
import lt.insoft.events.app.tag.entity.TagEntity;
import lt.insoft.events.app.tag.entity.TagEntity_;
import lt.insoft.events.model.dto.Response.EventListResponse;
import lt.insoft.events.model.dto.entity.EventListDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Repository
class EventRepoImpl extends SimpleJpaRepository<EventEntity, Long> implements EventRepoCustom {

    private final EntityManager em;

    EventRepoImpl(EntityManager em) {
        super(EventEntity.class, em);
        this.em = em;
    }

    @Override
    public EventListResponse searchEventsList(EventSearch search, PageRequest pageRequest) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<EventListDto> criteriaQuery = criteriaBuilder.createQuery(EventListDto.class);
        Root<EventEntity> root = criteriaQuery.from(EventEntity.class);

        Specification<EventEntity> specification = buildSpecification(search);

        criteriaQuery.multiselect(root.get(EventEntity_.name), root.get(EventEntity_.description), root.get(EventEntity_.id));

        if (!search.getTag().isBlank() || !search.getName().isBlank()) {
            criteriaQuery.where(specification.toPredicate(root, criteriaQuery, criteriaBuilder));
        }

        long count = count(specification);

        return getEventListResponse(pageRequest, criteriaQuery, count);
    }

    @NotNull
    private EventListResponse getEventListResponse(PageRequest pageRequest, CriteriaQuery<EventListDto> criteriaQuery, double count) {
        TypedQuery<EventListDto> typedQuery = em.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        typedQuery.setMaxResults(pageRequest.getPageSize());

        List<EventListDto> eventListDto = typedQuery.getResultList();

        return new EventListResponse(eventListDto, (int) Math.ceil(count / 10));
    }

    public static Predicate[] toArray(List<Predicate> predicates) {
        if (predicates == null) {
            throw new IllegalArgumentException("Parameter predicates is required");
        } else {
            return predicates.toArray(new Predicate[predicates.size()]);
        }
    }

    private Specification<EventEntity> buildSpecification(EventSearch search) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (search.getName() != null && !search.getName().isBlank()) {
                predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EventEntity_.name)), "%" + search.getName().toLowerCase(Locale.ROOT) + "%"));
            }

            if (search.getTag() != null && !search.getTag().isBlank()) {
                Subquery<EventEntity> eventSubQuery = criteriaQuery.subquery(EventEntity.class);
                Root<EventEntity> subQueryRoot = eventSubQuery.from(EventEntity.class);
                Join<EventEntity, TagEntity> eventTagJoin = subQueryRoot.join(EventEntity_.eventTag);

                eventSubQuery.select(subQueryRoot);
                eventSubQuery.where(criteriaBuilder
                        .and(criteriaBuilder
                                        .like(criteriaBuilder.lower(eventTagJoin.get(TagEntity_.tag)), "%" + search.getTag().toLowerCase(Locale.ROOT) + "%"),
                                criteriaBuilder.equal(root.get(EventEntity_.id), subQueryRoot.get(EventEntity_.id))));

                predicateList.add(criteriaBuilder.exists(eventSubQuery));
            }
            if (predicateList.isEmpty()) {
                return null;
            } else {
                return criteriaBuilder.or(toArray(predicateList));
            }
        };
    }
}
