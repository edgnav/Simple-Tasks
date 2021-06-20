package lt.insoft.events.app.tag.repository;

import lt.insoft.events.app.tag.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface TagRepoCustom  extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> getTagByName(String name);
}
