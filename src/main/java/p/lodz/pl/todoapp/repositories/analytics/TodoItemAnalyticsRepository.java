package p.lodz.pl.todoapp.repositories.analytics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import p.lodz.pl.todoapp.models.entities.TodoItem;

@Repository
@Transactional("analyticsTransactionManager")
public interface TodoItemAnalyticsRepository extends JpaRepository<TodoItem, Long> {
    TodoItem findByUuid(String uuid);
    TodoItem findByTitle(String title);
    void deleteByUuid(String uuid);
}
