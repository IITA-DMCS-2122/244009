package p.lodz.pl.todoapp.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TodoItemEditDto {

    private String uuid;

    private String title;

    private String description;

    @NotNull
    @Getter(AccessLevel.NONE)
    private Boolean realized;

    public Boolean isRealized() { return realized; }
}
