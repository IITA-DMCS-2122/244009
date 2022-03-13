package p.lodz.pl.todoapp.endpoints;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import p.lodz.pl.todoapp.entities.TodoPoint;
import p.lodz.pl.todoapp.repos.TodoPointRepo;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class TodoPointEndpoint {

    @Autowired
    TodoPointRepo todoPointRepo;

    @GetMapping("/todopoint")
    public ResponseEntity<List<TodoPoint>> getAllTodoPoints() {
        List<TodoPoint> todoPoints = todoPointRepo.findAll();
        return new ResponseEntity<>(todoPoints, HttpStatus.OK);
    }

    @GetMapping("/todopoint/{id}")
    public ResponseEntity<TodoPoint> getTodoPoint(@PathVariable("id") long id) {
        try {
            TodoPoint point = todoPointRepo.findById(id).orElseThrow();
            return new ResponseEntity<>(point, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/todopoint")
    public ResponseEntity<TodoPoint> addTodoPoint(@RequestBody @Valid TodoPoint todoPoint) {
        try {
            TodoPoint _todoPoint = todoPointRepo.save(todoPoint);
            return new ResponseEntity<>(_todoPoint, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping({"/todopoint", "/todopoint/{id}"})
    public ResponseEntity<TodoPoint> editTodoPoint(@PathVariable(required = false) Long id,
                                                   @RequestBody @Valid TodoPoint todoPoint) {
        TodoPoint foundTodoPoint;
        try {
            foundTodoPoint = todoPointRepo.findById(Objects.requireNonNullElseGet(id, todoPoint::getId)).orElse(null);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if(foundTodoPoint != null) {
            try {
                todoPoint.setId(foundTodoPoint.getId());
                TodoPoint _todoPoint = todoPointRepo.save(todoPoint);
                return new ResponseEntity<>(_todoPoint, HttpStatus.OK);
            }
            catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/todopoint/{id}")
    public ResponseEntity<String> deleteTodoPoint(@PathVariable("id") long id) {
        try {
            todoPointRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
