package p.lodz.pl.todoapp.repositories.primary;

import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Repository;
import p.lodz.pl.todoapp.models.TodoItem;
import p.lodz.pl.todoapp.models.entities.TodoItemEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoItemIndexedRepositoryImpl implements TodoItemIndexedRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TodoItemEntity> search(String query) {
        return Search.session(entityManager)
                .search(TodoItemEntity.class)
                .where(f -> f.match()
                        .fields("title", "description").matching(query)
                        .fuzzy())
                .fetchAllHits();
    }
}
