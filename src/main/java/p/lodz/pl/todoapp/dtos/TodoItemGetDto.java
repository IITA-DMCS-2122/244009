package p.lodz.pl.todoapp.dtos;

import lombok.Data;
import p.lodz.pl.todoapp.models.TodoItem;

@Data
public class TodoItemGetDto {

    private String uuid;

    private String title;

    private String description;

    private boolean realized;

    public TodoItemGetDto() {}
    public TodoItemGetDto(TodoItem todoItem) {
        uuid = todoItem.getUuid();
        title = todoItem.getTitle();
        description = todoItem.getDescription();
        realized = todoItem.isRealized();
    }
}
