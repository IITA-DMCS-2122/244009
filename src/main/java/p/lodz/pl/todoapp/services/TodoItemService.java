package p.lodz.pl.todoapp.services;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import p.lodz.pl.todoapp.dtos.TodoItemAddDto;
import p.lodz.pl.todoapp.dtos.TodoItemEditDto;
import p.lodz.pl.todoapp.models.TodoItem;
import p.lodz.pl.todoapp.models.TodoItemDocument;
import p.lodz.pl.todoapp.models.TodoItemEntity;
import p.lodz.pl.todoapp.repos.TodoItemDocumentRepository;
import p.lodz.pl.todoapp.repos.TodoItemEntityRepository;
import p.lodz.pl.todoapp.utils.TodoItemSource;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TodoItemService {

    private final TodoItemEntityRepository todoItemEntityRepository;
    private final TodoItemDocumentRepository todoItemDocumentRepository;

    public List<TodoItem> getAll() {
        // TODO: 25.03.2022 synchronize databases
        return getAll(TodoItemSource.POSTGRES);
    }

    public List<TodoItem> getAll(@NotNull TodoItemSource source) {
        List<TodoItem> todoItems;
        switch (source) {
            case POSTGRES:
                todoItems = new ArrayList<>(todoItemEntityRepository.findAll());
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
                todoItem = todoItemEntityRepository.findByUuid(uuid);
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
        todoItemEntityRepository.save(todoItemEntity);
        todoItemDocumentRepository.save(new TodoItemDocument(todoItemEntity));
        return todoItemEntity;
    }

    public TodoItem edit(@NotNull TodoItemEditDto todoItemEditDto) throws Exception {
        TodoItem todoItem = get(todoItemEditDto.getUuid());
        if(todoItem == null) throw new Exception("Not found");

        todoItem.update(todoItemEditDto);
        todoItemEntityRepository.save(new TodoItemEntity(todoItem));
        todoItemDocumentRepository.save(new TodoItemDocument(todoItem));

        return todoItem;
    }

    @Transactional
    public void delete(String uuid) {
        todoItemEntityRepository.deleteByUuid(uuid);
        todoItemDocumentRepository.deleteByUuid(uuid);
    }
}
