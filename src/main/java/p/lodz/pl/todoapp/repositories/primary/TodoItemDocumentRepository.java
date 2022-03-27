package p.lodz.pl.todoapp.repositories.primary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import p.lodz.pl.todoapp.models.documents.TodoItemDocument;

@Repository
@Transactional("mongoTransactionManager")
public interface TodoItemDocumentRepository extends MongoRepository<TodoItemDocument, Long> {
    TodoItemDocument findByUuid(String uuid);
    TodoItemDocument findByTitle(String title);
    void deleteByUuid(String uuid);
}
