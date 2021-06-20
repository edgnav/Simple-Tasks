package lt.insoft.events.app.events.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.insoft.events.app.tag.entity.TagEntity;
import lt.insoft.events.app.user.entity.UserEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "EVENT")
public class EventEntity {
    @Id
    @SequenceGenerator(name = "GEN_EVENTS", sequenceName = "SEQ_EVENTS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENTS")
    private Long id;
    private String name;
    private String description;
    private Date dateFrom;
    private Date dateTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID")
    private UserEntity creator;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EVENT_USER",
            joinColumns = { @JoinColumn(name = "EVENT_ID") },
            inverseJoinColumns = { @JoinColumn ( name= "USER_ID") }
    )
    private Set<UserEntity> userList = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EVENT_TAG",
            joinColumns = { @JoinColumn(name = "EVENT_ID") },
            inverseJoinColumns = { @JoinColumn ( name= "TAG_ID") }
    )
    private Set<TagEntity> eventTag = new HashSet<>();


}
