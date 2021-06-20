package lt.insoft.events.model.dto.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class EventListDto {
    private Long id;
    private String description;
    private String name;

    public EventListDto(String name, String description, long id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }
}