package p.lodz.pl.todoapp.repositories.analytics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import p.lodz.pl.todoapp.models.entities.TodoItemEntity;

@Repository
@Transactional("analyticsTransactionManager")
public interface TodoItemEntityAnalyticsRepository extends JpaRepository<TodoItemEntity, Long> {
    TodoItemEntity findByUuid(String uuid);
    TodoItemEntity findByTitle(String title);
    void deleteByUuid(String uuid);
}
