package p.lodz.pl.todoapp.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import p.lodz.pl.todoapp.models.TodoItem;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TodoItemAddDto {

    @NotNull
    @NotBlank
    private String title;

    private String description;

    @NotNull
    @Getter(AccessLevel.NONE)
    private Boolean realized;

//    public TodoItemAddDto() {}
//    public TodoItemAddDto(TodoItem todoItem) {
//        title = todoItem.getTitle();
//        description = todoItem.getDescription();
//        realized = todoItem.isRealized();
//    }

    public Boolean isRealized() { return realized; }
}
