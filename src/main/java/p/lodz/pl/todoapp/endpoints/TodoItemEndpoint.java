package p.lodz.pl.todoapp.endpoints;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import p.lodz.pl.todoapp.dtos.TodoItemAddDto;
import p.lodz.pl.todoapp.dtos.TodoItemEditDto;
import p.lodz.pl.todoapp.dtos.TodoItemGetDto;
import p.lodz.pl.todoapp.models.TodoItem;
import p.lodz.pl.todoapp.services.TodoItemService;
import p.lodz.pl.todoapp.utils.TodoItemSource;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TodoItemEndpoint {

    private final TodoItemService todoItemService;

    @GetMapping("/todoitem")
    public ResponseEntity<List<TodoItemGetDto>> getAllTodoItems(@RequestParam(required = false) String source) {
        List<TodoItem> todoItems;
        if(source == null) {
            todoItems = todoItemService.getAll();
        }
        else {
            switch(source) {
                case "postgres":
                    todoItems = todoItemService.getAll(TodoItemSource.POSTGRES);
                    break;
                case "mongo":
                    todoItems = todoItemService.getAll(TodoItemSource.MONGODB);
                    break;
                default:
                    return ResponseEntity.badRequest().build();
            }
        }

        List<TodoItemGetDto> todoItemDtos = todoItems.stream().map(TodoItemGetDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(todoItemDtos);
    }

    @GetMapping("/todoitem/{uuid}")
    public ResponseEntity<TodoItemGetDto> getTodoItem(@PathVariable("uuid") String uuid,
                                                      @RequestParam(required = false) String source) {
        TodoItem todoItem;
        if(source == null) {
            todoItem = todoItemService.get(uuid);
        }
        else {
            switch(source) {
                case "postgres":
                    todoItem = todoItemService.get(uuid, TodoItemSource.POSTGRES);
                    break;
                case "mongo":
                    todoItem = todoItemService.get(uuid, TodoItemSource.MONGODB);
                    break;
                default:
                    return ResponseEntity.badRequest().build();
            }
        }

        if(todoItem == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(new TodoItemGetDto(todoItem));
    }

    @PostMapping("/todoitem")
    public ResponseEntity<TodoItemGetDto> addTodoItem(@RequestBody @Valid TodoItemAddDto todoItemDto) {
        try {
            TodoItem todoItem = todoItemService.add(todoItemDto);
            return ResponseEntity.ok(new TodoItemGetDto(todoItem));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/todoitem")
    public ResponseEntity<TodoItemGetDto> editTodoItem(@RequestBody @Valid TodoItemEditDto todoItemEditDto) {
        TodoItem todoItem;
        try {
            todoItem = todoItemService.edit(todoItemEditDto);
            return ResponseEntity.ok(new TodoItemGetDto(todoItem));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/todoitem/{uuid}")
    public ResponseEntity<String> deleteTodoItem(@PathVariable("uuid") String uuid) {
        try {
            todoItemService.delete(uuid);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
