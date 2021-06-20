package lt.insoft.events.model.dto.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class EventDto {
    private String name;
    private String description;
    private Date dateFrom;
    private Date dateTo;
    private String creator;
    private Set<String> userList;
    private Set<String> tags;


    public EventDto(String name, String description, Date dateFrom, Date dateTo, Set<String> userList, Set<String>  tags) {
        this.name = name;
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.userList = userList;
        this.tags = tags;
    }
}





