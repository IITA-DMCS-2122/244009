package p.lodz.pl.todoapp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import p.lodz.pl.todoapp.dtos.TodoItemAddDto;
import p.lodz.pl.todoapp.models.documents.Event;
import p.lodz.pl.todoapp.models.documents.EventOperation;
import p.lodz.pl.todoapp.repositories.primary.EventRepository;

@Service
@AllArgsConstructor
public class TodoItemQueueService {

    private final EventRepository eventRepository;

    public void add(TodoItemAddDto todoItemAddDto) {
        eventRepository.save(new Event(EventOperation.ADD, todoItemAddDto));
    }

    public long getCount() {
        return eventRepository.count();
    }
}
