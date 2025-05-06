package com.controllers;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Admin;
import com.entities.Dean;
import com.entities.DepartmentChair;
import com.entities.DepartmentStaff;
import com.entities.Instructor;
import com.entities.TA;
import com.entities.User;
import com.services.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final DeanService deanService;
    private final DepartmentStaffService departmentStaffService;
    private final DepartmentChairService departmentChairService;
    private final InstructorService instructorService;
    private final UserService userService;
    private final AdminsService adminsService;
    private final TAService taService;
    public UserController(UserService userService, AdminsService adminsService, TAService taService, InstructorService instructorService, 
    DepartmentStaffService departmentStaffService, DepartmentChairService departmentChairService, DeanService deanService) {
        this.deanService = deanService;
        this.departmentChairService = departmentChairService;
        this.userService = userService;
        this.adminsService = adminsService;
        this.taService = taService;
        this.instructorService = instructorService;
        this.departmentStaffService = departmentStaffService;

    }


    @GetMapping("/users")
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            if (user instanceof Admin) {
        Admin admin = (Admin) user;
        return adminsService.createAdmin(
            admin.getUsername(),
            admin.getName(),
            admin.getEmail(),
            admin.getPassword()
        );
        
    } else if (user instanceof TA) {
        TA ta = (TA) user;
        return taService.createTA(
            ta.getName(),
            ta.getEmail(),
            ta.getUsername(),
            ta.getPassword(),
            ta.getCurrentAssistingCourses(),
            ta.getCurrentTakingCourses(),
            ta.getAdvisor(),
            ta.getTotalWorkload(),
            ta.getProctoringExams()
        );
    } else if (user instanceof Instructor) {
        Instructor instructor = (Instructor) user;
        return instructorService.createInstructor(
                instructor.getName(),
                instructor.getEmail(),
                instructor.getUsername(),
                instructor.getPassword(),
                instructor.getCourses(),
                instructor.getTas(),
                instructor.getDepartmentCode(),
                instructor.getTitle()
        );
    } else if (user instanceof DepartmentStaff) {
        DepartmentStaff departmentStaff = (DepartmentStaff) user;
        return departmentStaffService.createDepartmentStaff(
            departmentStaff.getName(),
            departmentStaff.getEmail(),
            departmentStaff.getUsername(),
            departmentStaff.getPassword(),
            departmentStaff.getDepartmentCode(), 
            departmentStaff.getTitle()
        );
    } else if (user instanceof DepartmentChair) {
        DepartmentChair departmentChair = (DepartmentChair) user;
        return departmentChairService.createDepartmentChair(
            departmentChair.getName(),
            departmentChair.getEmail(),
            departmentChair.getUsername(),
            departmentChair.getPassword(),
            departmentChair.getDepartmentCode(),
            departmentChair.getTitle()
        );

    } else if (user instanceof Dean) {
        Dean dean = (Dean) user;
        return deanService.createDean(
            dean.getName(),
            dean.getEmail(),
            dean.getUsername(),
            dean.getPassword(),
            dean.getDepartmentCode(),
            dean.getTitle()
        );
        
    } else {
        // fallback or throw error
        return userService.createUser(
            user.getName(),
            user.getEmail(),
            user.getUsername(),
            user.getPassword()
        );

    }
}
        // user crudları create gibi yapılacak
    @PutMapping("/update-user/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        return userService.updateUserById(id, user);
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return "User deleted successfully!";
    }

    @GetMapping("/user/{id}")      
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

}
