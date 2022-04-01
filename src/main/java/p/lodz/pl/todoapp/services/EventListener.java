package p.lodz.pl.todoapp.services;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import p.lodz.pl.todoapp.dtos.TodoItemAddDto;
import p.lodz.pl.todoapp.models.documents.Event;
import p.lodz.pl.todoapp.models.documents.EventOperation;
import p.lodz.pl.todoapp.models.documents.EventStatus;
import p.lodz.pl.todoapp.repositories.primary.EventRepository;

@Component
@Transactional
@EnableMongoAuditing
@AllArgsConstructor
public class EventListener extends AbstractMongoEventListener<Event> {

    private final TodoItemService todoItemService;
    private final EventRepository eventRepository;

    @Override
    public void onAfterSave(AfterSaveEvent<Event> e) {
        Event event = e.getSource();
        if(event.getStatus() == EventStatus.PENDING) {
            switch(event.getOperation()) {
                case ADD:
                    Object obj = event.getPayload();
                    if (obj instanceof TodoItemAddDto) {
                        TodoItemAddDto todoItemAddDto = (TodoItemAddDto) obj;
                        todoItemService.add(todoItemAddDto);
                        event.setStatus(EventStatus.REALIZED);
                        eventRepository.save(event);
                    }
                    break;
            }
        }
    }
}
