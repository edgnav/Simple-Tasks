package lt.insoft.events.app.user.repository;

import lt.insoft.events.app.user.entity.UserEntity;
import lt.insoft.events.app.user.entity.UserEntity_;
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
 class UserRepoImpl extends SimpleJpaRepository<UserEntity, Long> implements UserRepoCustom {
    private final EntityManager em;

    UserRepoImpl(EntityManager em) {
        super(UserEntity.class, em);
        this.em = em;
    }
    @Override
    public Optional<UserEntity> findByEmail(String email){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();

        Root<UserEntity> u = cq.from(UserEntity.class);

        Predicate userNamePredicate = cb.equal(u.get(UserEntity_.EMAIL), email);

        cq.select(u).where(userNamePredicate);

        TypedQuery<UserEntity> query = em.createQuery(cq);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
