package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTest {
    @Autowired
    private DbService dbService;

    @Test
    public void saveTaskTest() {
        //Given
        Task taskB1 = new Task("Pierwsze zadanie", "Pierwszy opis");
        //When
        Task taskB2 = dbService.saveTask(taskB1);
        //Then
        assertEquals("Pierwszy opis", taskB2.getContent());
        //clean up
        dbService.deleteTask(taskB2.getId());
    }

    @Test
    public void getTask() {
        //Given
        Task taskB1 = new Task("Pierwsze zadanie", "Pierwszy opis");
        Task taskB2 = dbService.saveTask(taskB1);
        //When
        Optional<Task> taskWithId = dbService.getTask(taskB2.getId());
        //Then
        assertTrue(taskWithId.isPresent());
        //clean up
        dbService.deleteTask(taskWithId.get().getId());
    }

    @Test
    public void deleteTaskTest() {
        //Given
        Task taskB1 = new Task("Szóste zadanie", "Szósty opis");
        dbService.saveTask(taskB1);
        //When
        dbService.deleteTask(taskB1.getId());
        Optional<Task> optionalTask = dbService.getTask(taskB1.getId());
        //Then
        assertFalse(optionalTask.isPresent());
    }
    @Test
    public void getAllTasksTest() {
        //Given
        Task taskB1 = new Task("Pierwsze zadanie", "Pierwszy opis");
        Task taskB2 = new Task("Drugie zadanie", "Drugi opis");
        List<Task> allTasks1 = dbService.getAllTasks();
        dbService.saveTask(taskB1);
        dbService.saveTask(taskB2);
        //When
        List<Task> allTasks2 = dbService.getAllTasks();
        //Then
        assertEquals(allTasks1.size()+2, allTasks2.size());
        //clean up
        dbService.deleteTask(taskB1.getId());
        dbService.deleteTask(taskB2.getId());

    }
}