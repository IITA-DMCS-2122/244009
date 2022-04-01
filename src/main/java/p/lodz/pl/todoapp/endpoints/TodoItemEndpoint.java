package p.lodz.pl.todoapp.endpoints;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import p.lodz.pl.todoapp.dtos.TodoItemAddDto;
import p.lodz.pl.todoapp.dtos.TodoItemCountDto;
import p.lodz.pl.todoapp.dtos.TodoItemEditDto;
import p.lodz.pl.todoapp.dtos.TodoItemGetDto;
import p.lodz.pl.todoapp.models.entities.TodoItem;
import p.lodz.pl.todoapp.services.TodoItemQueueService;
import p.lodz.pl.todoapp.services.TodoItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TodoItemEndpoint {

    private final TodoItemService todoItemService;
    private final TodoItemQueueService todoItemQueue;

    @GetMapping("/todoitem")
    public ResponseEntity<List<TodoItemGetDto>> getAllTodoItems() {
        return ResponseEntity.ok(todoItemService.getAll()
                .stream().map(TodoItemGetDto::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/todoitem/{uuid}")
    public ResponseEntity<TodoItemGetDto> getTodoItem(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(new TodoItemGetDto(todoItemService.get(uuid)));
    }

    @PostMapping("/todoitem")
    public ResponseEntity<String> addTodoItem(@RequestBody @Valid TodoItemAddDto todoItemDto) {
        todoItemQueue.add(todoItemDto);
        return ResponseEntity.ok().build();
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

    @GetMapping("todoitem/search")
    public ResponseEntity<List<TodoItemGetDto>> search(@RequestParam("query") String query) {
        return ResponseEntity.ok(todoItemService.search(query));
    }

    @GetMapping("todoitem/count")
    public ResponseEntity<TodoItemCountDto> getCount() {
        long count = todoItemService.getCount();
        TodoItemCountDto dto = new TodoItemCountDto();
        dto.setCount(count);
        return ResponseEntity.ok(dto);
    }
}
