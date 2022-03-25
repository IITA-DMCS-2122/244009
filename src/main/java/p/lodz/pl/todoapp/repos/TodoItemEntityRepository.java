package p.lodz.pl.todoapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import p.lodz.pl.todoapp.models.TodoItemEntity;

@Repository
public interface TodoItemEntityRepository extends JpaRepository<TodoItemEntity, Long> {
    TodoItemEntity findByUuid(String uuid);
    TodoItemEntity findByTitle(String title);
    void deleteByUuid(String uuid);
}
