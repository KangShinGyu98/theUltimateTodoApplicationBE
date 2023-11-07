package com.projectss.theUltimateTodo.todo.domain;

import com.projectss.theUltimateTodo.todo.dto.TodoDTO;
import com.projectss.theUltimateTodo.todo.service.TodoService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String content;
    private TodoStatus status;
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;
    private LocalTime startTime;
    @Future
    private LocalTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void add(TodoDTO todoDTO) {

        if (ObjectUtils.isEmpty(todoDTO.startDate())) {
            this.defaultStartDateTime();
        }
        else {
            this.startDate = todoDTO.startDate();
            this.startTime = todoDTO.startTime();
        }

        if (ObjectUtils.isEmpty(todoDTO.endDate())) {
            this.defaultEndDateTime();
        }
        else {
            this.endDate = todoDTO.endDate();
            this.endTime = todoDTO.endTime();
        }

        this.id = todoDTO.id();
        this.content = todoDTO.content();
        this.status = TodoStatus.NOT_DONE;
        this.createdAt = LocalDateTime.now();
    }

    public void defaultStartDateTime() {
        this.startDate = LocalDate.now();
        this.startTime = LocalTime.of(0, 0);
    }

    public void defaultEndDateTime() {
        this.endDate = LocalDate.now().plusDays(1);
        this.endTime = LocalTime.of(0, 0);

    }

    public void update(TodoService service, TodoDTO todoDTO) {
        Todo todo = service.searchById(todoDTO.id());
        todo.updateTodo(todoDTO.content(), todoDTO.status(), todoDTO.startDate(), todoDTO.endDate(), todoDTO.startTime(), todoDTO.endTime());
    }

    public void updateTodo(String content, TodoStatus status, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.content = content;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.updatedAt = LocalDateTime.now();

    }
}
