package p.lodz.pl.todoapp.services;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import p.lodz.pl.todoapp.dtos.TodoItemAddDto;
import p.lodz.pl.todoapp.dtos.TodoItemEditDto;
import p.lodz.pl.todoapp.dtos.TodoItemGetDto;
import p.lodz.pl.todoapp.models.TodoItem;
import p.lodz.pl.todoapp.models.documents.TodoItemDocument;
import p.lodz.pl.todoapp.models.entities.TodoItemEntity;
import p.lodz.pl.todoapp.repositories.analytics.TodoItemEntityAnalyticsRepository;
import p.lodz.pl.todoapp.repositories.primary.TodoItemDocumentRepository;
import p.lodz.pl.todoapp.repositories.primary.TodoItemEntityPrimaryRepository;
import p.lodz.pl.todoapp.repositories.primary.TodoItemIndexedRepository;
import p.lodz.pl.todoapp.utils.TodoItemSource;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoItemService {

    private final TodoItemEntityPrimaryRepository todoItemEntityPrimaryRepository;
    private final TodoItemEntityAnalyticsRepository todoItemEntityAnalyticsRepository;
    private final TodoItemDocumentRepository todoItemDocumentRepository;
    private final TodoItemIndexedRepository todoItemIndexedRepository;

    public List<TodoItem> getAll() {
        // TODO: 25.03.2022 synchronize databases
        return getAll(TodoItemSource.POSTGRES);
    }

    public List<TodoItem> getAll(@NotNull TodoItemSource source) {
        List<TodoItem> todoItems;
        switch (source) {
            case POSTGRES:
                todoItems = new ArrayList<>(todoItemEntityPrimaryRepository.findAll());
                break;
            case MONGODB:
                todoItems = new ArrayList<>(todoItemDocumentRepository.findAll());
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return todoItems;
    }

    public TodoItem get(String uuid) {
        TodoItem todoItem = get(uuid, TodoItemSource.POSTGRES);
        if(todoItem == null) todoItem = get(uuid, TodoItemSource.MONGODB);
        return todoItem;
    }

    public TodoItem get(String uuid, @NotNull TodoItemSource source) {
        TodoItem todoItem;
        switch (source) {
            case POSTGRES:
                todoItem = todoItemEntityPrimaryRepository.findByUuid(uuid);
                break;
            case MONGODB:
                todoItem = todoItemDocumentRepository.findByUuid(uuid);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return todoItem;
    }

    public TodoItem add(TodoItemAddDto todoItemAddDto) {
        TodoItemEntity todoItemEntity = new TodoItemEntity(todoItemAddDto);
        todoItemEntityPrimaryRepository.save(todoItemEntity);
        todoItemEntityAnalyticsRepository.save(todoItemEntity);
        todoItemDocumentRepository.save(new TodoItemDocument(todoItemEntity));
        return todoItemEntity;
    }

    public TodoItem edit(@NotNull TodoItemEditDto todoItemEditDto) throws Exception {
        TodoItem todoItem = get(todoItemEditDto.getUuid());
        if(todoItem == null) throw new Exception("Not found");

        todoItem.update(todoItemEditDto);
        todoItemEntityPrimaryRepository.save(new TodoItemEntity(todoItem));
        todoItemEntityAnalyticsRepository.save(new TodoItemEntity(todoItem));
        todoItemDocumentRepository.save(new TodoItemDocument(todoItem));

        return todoItem;
    }

    public void delete(String uuid) {
        todoItemEntityPrimaryRepository.deleteByUuid(uuid);
        todoItemDocumentRepository.deleteByUuid(uuid);
    }

    public List<TodoItemGetDto> search(String query) {
        return todoItemIndexedRepository.search(query).stream().map(TodoItemGetDto::new).collect(Collectors.toList());
    }
}
