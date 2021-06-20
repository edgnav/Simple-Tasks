package lt.insoft.events.app.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.insoft.events.app.events.entity.EventEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "USER_INFO")
public class UserEntity {
    @Id
    @SequenceGenerator(name = "GEN_USER_INFO", sequenceName = "SEQ_USER_INFO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_USER_INFO")
    private Long id;
    private String email;
    private String hash;

}
