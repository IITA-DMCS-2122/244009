package p.lodz.pl.todoapp.repositories.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import p.lodz.pl.todoapp.models.entities.TodoItemEntity;

@Repository
@Transactional("primaryTransactionManager")
public interface TodoItemEntityPrimaryRepository extends JpaRepository<TodoItemEntity, Long> {
    TodoItemEntity findByUuid(String uuid);
    TodoItemEntity findByTitle(String title);
    void deleteByUuid(String uuid);
}
