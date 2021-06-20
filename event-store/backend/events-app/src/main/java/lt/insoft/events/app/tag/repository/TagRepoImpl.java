package lt.insoft.events.app.tag.repository;

import lt.insoft.events.app.tag.entity.TagEntity;
import lt.insoft.events.app.tag.entity.TagEntity_;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
class TagRepoImpl extends SimpleJpaRepository<TagEntity, Long> implements TagRepoCustom {
    private final EntityManager em;

    TagRepoImpl(EntityManager em) {
        super(TagEntity.class, em);
        this.em = em;
    }
    @Override
    public Optional<TagEntity> getTagByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();

        Root<TagEntity> t = cq.from(TagEntity.class);

        Predicate tagNamePredicate = cb.equal(t.get(TagEntity_.tag), name);

        cq.select(t).where(tagNamePredicate);

        TypedQuery<TagEntity> query = em.createQuery(cq);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
