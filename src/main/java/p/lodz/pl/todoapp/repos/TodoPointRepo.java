package p.lodz.pl.todoapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import p.lodz.pl.todoapp.entities.TodoPoint;

public interface TodoPointRepo extends JpaRepository<TodoPoint, Long> {
}
