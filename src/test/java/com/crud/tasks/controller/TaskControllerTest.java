package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@RunWith(SpringRunner.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchTasks() throws Exception{
        // Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(21L, "Test taskDto title", "Test taskDto content"));
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(new Task("Task title", "Task content"));

        Mockito.when(dbService.getAllTasks()).thenReturn(tasksList);
        Mockito.when(taskMapper.mapToTaskDtoList(anyListOf(Task.class))).thenReturn(taskDtoList);
        // When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(21)))
                .andExpect(jsonPath("$[0].title", Matchers.is("Test taskDto title")))
                .andExpect(jsonPath("$[0].content", Matchers.is("Test taskDto content")));
    }
    @Test
    public void shouldFetchTask() throws Exception{
        // Given
        TaskDto taskDto1 = new TaskDto(21L, "Test DTO Task", "Test DTO Content");
        Task task1 = new Task("Test Task", "Test Content");

        Mockito.when(dbService.getTask(taskDto1.getId())).thenReturn(Optional.ofNullable(task1));
        Mockito.when(taskMapper.mapToTaskDto(task1)).thenReturn(taskDto1);
        // When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=21")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(21)))
                .andExpect(jsonPath("$.title", Matchers.is("Test DTO Task")))
                .andExpect(jsonPath("$.content", Matchers.is("Test DTO Content")));
    }
    @Test
    public void shouldCreateTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(21L, "Test DTO Task", "Test DTO Content");
        Task task1 = new Task("Test Task", "Test Content");

        Mockito.when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));

        Mockito.verify(dbService, Mockito.times(1)).saveTask(task1);

    }
    @Test
    public void shouldUpdateTask() throws Exception {
        TaskDto taskDto = new TaskDto(21L, "Test DTO task", "Test DTO content");
        Task task1 = new Task("Test Task", "Test Content");

        Mockito.when(dbService.saveTask(task1)).thenReturn(task1);
        Mockito.when(taskMapper.mapToTaskDto(any())).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.is("Test DTO content")));
    }
    @Test
    public void shouldDeleteTask() throws Exception{
        //Given
        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=21")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(dbService, Mockito.times(1)).deleteTask(21L);
    }
}
