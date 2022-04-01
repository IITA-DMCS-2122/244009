package p.lodz.pl.todoapp.models.documents;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import p.lodz.pl.todoapp.dtos.TodoItemAddDto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document(collection = "events")
@Data
public class Event {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private ObjectId id;

    @NotNull
    private LocalDateTime creationDate;

    @NotNull
    private EventOperation operation;

    @NotNull
    private EventStatus status;

    private Object payload;

    public Event() {}

    public Event(EventOperation operation, Object payload) {
        this.setCreationDate(LocalDateTime.now());
        this.setOperation(operation);
        this.setStatus(EventStatus.PENDING);
        this.setPayload(payload);
    }

}
