package lt.insoft.events.app.user.repository;

import lt.insoft.events.app.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepoCustom extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
