package p.lodz.pl.todoapp.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import p.lodz.pl.todoapp.models.TodoItemDocument;

@Repository
public interface TodoItemDocumentRepository extends MongoRepository<TodoItemDocument, Long> {
    TodoItemDocument findByUuid(String uuid);
    TodoItemDocument findByTitle(String title);
    void deleteByUuid(String uuid);
}
