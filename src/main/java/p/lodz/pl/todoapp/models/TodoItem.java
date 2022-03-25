package p.lodz.pl.todoapp.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import p.lodz.pl.todoapp.dtos.TodoItemEditDto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface TodoItem {

    Long getId();
    void setId(Long id);

    String getUuid();
    void setUuid(String uuid);

    String getTitle();
    void setTitle(String title);

    String getDescription();
    void setDescription(String description);

    Boolean isRealized();
    void setRealized(Boolean realized);

    void update(TodoItemEditDto todoItemEditDto);

}
