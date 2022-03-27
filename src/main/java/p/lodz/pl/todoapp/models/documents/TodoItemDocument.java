package p.lodz.pl.todoapp.models.documents;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import p.lodz.pl.todoapp.dtos.TodoItemEditDto;
import p.lodz.pl.todoapp.models.TodoItem;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document(collection = "todoItem")
@Data
public class TodoItemDocument implements TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @Column(unique = true)
    private String uuid;

    @NotNull
    @NotBlank
    private String title;

    private String description;

    @NotNull
    @Getter(AccessLevel.NONE)
    private Boolean realized;

    public Boolean isRealized() { return realized; }

    public TodoItemDocument() {}

    public TodoItemDocument(TodoItem todoItem) {
        this.id = todoItem.getId();
        this.uuid = todoItem.getUuid();
        this.title = todoItem.getTitle();
        this.description = todoItem.getDescription();
        this.realized = todoItem.isRealized();
    }

    public void update(TodoItemEditDto todoItemEditDto) {
        this.title = todoItemEditDto.getTitle();
        this.description = todoItemEditDto.getDescription();
        this.realized = todoItemEditDto.isRealized();
    }
}
