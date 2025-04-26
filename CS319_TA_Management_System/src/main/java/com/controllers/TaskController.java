package com.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.entities.Task;
import com.entities.Course;
import com.entities.TA;
import com.entities.User;
import com.services.TaskService;
import com.services.CoursesService;
import com.services.TAService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    private CoursesService coursesService;

    private TAService taService;

    public TaskController(TaskService taskService, CoursesService coursesService, TAService taService) {
        this.taskService = taskService;
        this.coursesService = coursesService;
        this.taService = taService;
    }

    @GetMapping("/test")
    public String test() {
        return "task works";
    }

    @GetMapping("/list")
    public List<Task> listTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable Integer taskId) {
        return taskService.getTaskById(taskId);
    }

    @DeleteMapping("/delete/{taskId}")

    public void deleteTask(@PathVariable Integer taskId) {

        taskService.deleteTaskById(taskId);
    }

    @PutMapping("/update/{taskId}")
    public Task updateTask(@PathVariable Integer taskId, @RequestBody Task task) {
        return taskService.updateTask(taskId, task);
    }

    @PostMapping("/submit")
    public boolean submitTask(
            @RequestParam String taskDate,
            @RequestParam Integer taskDuration,
            @RequestParam Integer courseId,
            @RequestParam String description,
            @RequestParam Integer taId) {

        Course course = coursesService.getCourseById(courseId);

        TA ta = taService.getTAById(taId);

        if (course != null && ta != null) {
            return taskService.submitTask(taskDate,
                    taskDuration, course, description,
                    ta);
        }
        return false;
    }

    @GetMapping("/view/{taskId}")
    public boolean viewTask(@PathVariable Integer taskId) {

        Task task = taskService.getTaskById(taskId);
        return taskService.viewTask(task);
    }

    @PostMapping("/approve/{taskId}")
    public boolean approveTask(
            @PathVariable Integer taskId,
            @RequestParam Integer instructorId) {

        // todo
        Task task = taskService.getTaskById(taskId);
        User instructor = null;

        if (task != null && instructor != null) {
            return taskService.approveTask(task, instructor);
        }
        return false;
    }

    @PostMapping("/reject/{taskId}")
    public boolean rejectTask(
            @PathVariable Integer taskId,
            @RequestParam Integer instructorId) {

        Task task = taskService.getTaskById(taskId);
        User instructor = null; // todo
        if (task != null && instructor != null) {
            return taskService.rejectTask(task, instructor);
        }
        return false;
    }

    // Needed to filter tasks by Date

    // @GetMapping("/by-date")
    // public List<Task> getTasksByDate(@RequestParam String date) {
    // return taskService.getTasksByDate(date);
    // }
}