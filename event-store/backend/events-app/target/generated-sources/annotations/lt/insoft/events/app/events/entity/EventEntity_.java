package lt.insoft.events.app.events.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import lt.insoft.events.app.tag.entity.TagEntity;
import lt.insoft.events.app.user.entity.UserEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EventEntity.class)
public abstract class EventEntity_ {

	public static volatile SingularAttribute<EventEntity, UserEntity> creator;
	public static volatile SetAttribute<EventEntity, UserEntity> userList;
	public static volatile SetAttribute<EventEntity, TagEntity> eventTag;
	public static volatile SingularAttribute<EventEntity, String> name;
	public static volatile SingularAttribute<EventEntity, Date> dateTo;
	public static volatile SingularAttribute<EventEntity, String> description;
	public static volatile SingularAttribute<EventEntity, Long> id;
	public static volatile SingularAttribute<EventEntity, Date> dateFrom;

	public static final String CREATOR = "creator";
	public static final String USER_LIST = "userList";
	public static final String EVENT_TAG = "eventTag";
	public static final String NAME = "name";
	public static final String DATE_TO = "dateTo";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String DATE_FROM = "dateFrom";

}

