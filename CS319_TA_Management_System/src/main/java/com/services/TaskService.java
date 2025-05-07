package com.services;

import org.springframework.stereotype.Service;

import com.entities.Task;
import com.entities.Course;
import com.entities.TA;
import com.entities.User;
import com.repositories.TaskRepository;
import com.repositories.CoursesRepository;
import com.repositories.TARepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CoursesRepository coursesRepository;
    private final TARepository taRepository;

    public TaskService(TaskRepository taskRepository, CoursesRepository coursesRepository, TARepository taRepository) {
        this.taskRepository = taskRepository;
        this.coursesRepository = coursesRepository;

        this.taRepository = taRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Integer taskId) {
        return taskRepository.findById(taskId).orElse(null);

    }

    public void deleteTaskById(Integer taskId) {

        taskRepository.deleteById(taskId);
    }

    public Task updateTask(Integer taskId, Task task) {
        Task existingTask = taskRepository.findById(taskId).orElse(null);
        if (existingTask != null) {
            existingTask.setTaskDate(task.getTaskDate());
            existingTask.setTaskDuration(task.getTaskDuration());
            existingTask.setCourse(task.getCourse());
            existingTask.setDescription(task.getDescription());
            existingTask.setTa(task.getTa());
            return taskRepository.save(existingTask);
        }
        return null;
        // avoiding errors?
    }

    // TaskService methods
    public boolean submitTask(String taskDate, Integer taskDuration, Course course, String description, TA ta) {
        try {
            Task newTask = new Task();
            newTask.setTaskDate(taskDate);
            newTask.setTaskDuration(taskDuration);
            newTask.setCourse(course);
            newTask.setDescription(description);
            newTask.setTa(ta);

            taskRepository.save(newTask);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean viewTask(Task task) {
        // gotta look into
        return task != null && taskRepository.findById(task.getTaskId()).isPresent();
    }

    public boolean approveTask(Task task, User instructor) {
        try {
            Task existingTask = taskRepository.findById(task.getTaskId()).orElse(null);
            if (existingTask != null) {
                // todo

                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean rejectTask(Task task, User instructor) {
        try {
            Task existingTask = taskRepository.findById(task.getTaskId()).orElse(null);
            if (existingTask != null) {
                // todo

                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    // filter methods

    // public List<Task> getTasksByDate(String date) {
    //     return taskRepository.findByTaskDate(date);
    // }
}