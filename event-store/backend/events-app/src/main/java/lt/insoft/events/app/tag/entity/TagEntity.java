package lt.insoft.events.app.tag.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "TAG")
public class TagEntity {
    @Id
    @SequenceGenerator(name = "GEN_TAGS", sequenceName = "SEQ_TAGS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TAGS")
    private Long id;
    private String tag;
}
