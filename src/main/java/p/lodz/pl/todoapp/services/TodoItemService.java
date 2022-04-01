package p.lodz.pl.todoapp.services;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import p.lodz.pl.todoapp.dtos.TodoItemAddDto;
import p.lodz.pl.todoapp.dtos.TodoItemEditDto;
import p.lodz.pl.todoapp.dtos.TodoItemGetDto;
import p.lodz.pl.todoapp.models.entities.TodoItem;
import p.lodz.pl.todoapp.repositories.analytics.TodoItemAnalyticsRepository;
import p.lodz.pl.todoapp.repositories.primary.EventRepository;
import p.lodz.pl.todoapp.repositories.primary.TodoItemPrimaryRepository;
import p.lodz.pl.todoapp.repositories.primary.TodoItemIndexedRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoItemService {

    private final TodoItemPrimaryRepository todoItemPrimaryRepository;
    private final TodoItemAnalyticsRepository todoItemAnalyticsRepository;
    private final EventRepository eventRepository;
    private final TodoItemIndexedRepository todoItemIndexedRepository;

    public List<TodoItem> getAll() {
        return todoItemPrimaryRepository.findAll();
    }

    public TodoItem get(String uuid) {
        return todoItemPrimaryRepository.findByUuid(uuid);
    }

    public TodoItem add(TodoItemAddDto todoItemAddDto) {
        TodoItem todoItem = new TodoItem(todoItemAddDto);
        todoItemPrimaryRepository.save(todoItem);
        todoItemAnalyticsRepository.save(todoItem);
        return todoItem;
    }

    public TodoItem edit(@NotNull TodoItemEditDto todoItemEditDto) throws Exception {
        TodoItem todoItem = get(todoItemEditDto.getUuid());
        if(todoItem == null) throw new Exception("Not found");

        todoItem.update(todoItemEditDto);
        todoItemPrimaryRepository.save(new TodoItem(todoItem));
        todoItemAnalyticsRepository.save(new TodoItem(todoItem));

        return todoItem;
    }

    public void delete(String uuid) {
        todoItemPrimaryRepository.deleteByUuid(uuid);
    }

    public List<TodoItemGetDto> search(String query) {
        return todoItemIndexedRepository.search(query).stream().map(TodoItemGetDto::new).collect(Collectors.toList());
    }

    public long getCount() {
        return eventRepository.count();
    }
}
