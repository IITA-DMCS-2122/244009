package p.lodz.pl.todoapp.models.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import p.lodz.pl.todoapp.dtos.TodoItemAddDto;
import p.lodz.pl.todoapp.dtos.TodoItemEditDto;
import p.lodz.pl.todoapp.models.TodoItem;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity(name = "todo_item")
@Indexed
@Data
public class TodoItemEntity implements TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @Column(unique = true)
    private String uuid;

    @NotNull
    @NotBlank
    @FullTextField
    private String title;

    @FullTextField
    private String description;

    @NotNull
    @Getter(AccessLevel.NONE)
    private Boolean realized;

    public Boolean isRealized() { return realized; }

    public TodoItemEntity() {}

    public TodoItemEntity(TodoItem todoItem) {
        this.id = todoItem.getId();
        this.uuid = todoItem.getUuid();
        this.title = todoItem.getTitle();
        this.description = todoItem.getDescription();
        this.realized = todoItem.isRealized();
    }

    public TodoItemEntity(TodoItemAddDto todoItemAddDto) {
        this.setId(0L);
        this.setUuid(UUID.randomUUID().toString());
        this.setTitle(todoItemAddDto.getTitle());
        this.setDescription(todoItemAddDto.getDescription());
        this.setRealized(todoItemAddDto.isRealized());
    }

    public void update(TodoItemEditDto todoItemEditDto) {
        if(todoItemEditDto.getTitle() != null && todoItemEditDto.getTitle().isBlank())
            this.title = todoItemEditDto.getTitle();
        if(todoItemEditDto.getDescription() != null)
            this.description = todoItemEditDto.getDescription();
        if(todoItemEditDto.isRealized() != null)
            this.realized = todoItemEditDto.isRealized();
    }
}
