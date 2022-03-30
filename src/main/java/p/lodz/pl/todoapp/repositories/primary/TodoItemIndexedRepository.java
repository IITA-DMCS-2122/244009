package p.lodz.pl.todoapp.repositories.primary;

import org.springframework.stereotype.Repository;
import p.lodz.pl.todoapp.models.TodoItem;
import p.lodz.pl.todoapp.models.entities.TodoItemEntity;

import java.util.List;

@Repository
public interface TodoItemIndexedRepository {
    List<TodoItemEntity> search(String query);
}
