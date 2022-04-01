package p.lodz.pl.todoapp.repositories.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import p.lodz.pl.todoapp.models.entities.TodoItem;

@Repository
@Transactional("primaryTransactionManager")
public interface TodoItemPrimaryRepository extends JpaRepository<TodoItem, Long> {
    TodoItem findByUuid(String uuid);
    TodoItem findByTitle(String title);
    void deleteByUuid(String uuid);
}
