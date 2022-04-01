package p.lodz.pl.todoapp.repositories.primary;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import p.lodz.pl.todoapp.models.documents.Event;

@Repository
@Transactional("mongoTransactionManager")
public interface EventRepository extends MongoRepository<Event, Long> {

}
