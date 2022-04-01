package p.lodz.pl.todoapp.repositories.primary;

import org.springframework.stereotype.Repository;
import p.lodz.pl.todoapp.models.entities.TodoItem;

import java.util.List;

@Repository
public interface TodoItemIndexedRepository {
    List<TodoItem> search(String query);
}
