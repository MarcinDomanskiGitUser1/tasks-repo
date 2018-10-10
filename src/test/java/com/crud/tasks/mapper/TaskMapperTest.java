package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void mapToTaskTest() {
        //Given
        TaskDto taskDtoB1 = new TaskDto(21L, "Pierwsze zadanie", "Pierwszy Opis Zadania");
        //When
        Task taskE1 = taskMapper.mapToTask(taskDtoB1);
        //Then
        assertEquals("Pierwsze zadanie", taskE1.getTitle());
    }

    @Test
    public void mapToTaskDto() {
        //Given
        Task taskB1 = new Task("Pierwsze zadanie", "Pierwszy opis");
        //When
        TaskDto taskDtoE2 = taskMapper.mapToTaskDto(taskB1);
        //Then
        assertEquals("Pierwsze zadanie", taskDtoE2.getTitle());
    }

    @Test
    public void mapToTaskDtoList() {
        //Given
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(new Task("Takie zadanie", "Taki opis") );
        tasksList.add(new Task("Inne zadanie", "Inny opis"));
        //When
        List<TaskDto> dtoTasksList = taskMapper.mapToTaskDtoList(tasksList);
        //Then
        assertEquals("Inny opis", dtoTasksList.get(1).getContent());
    }
}