package com.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Admin;
import com.entities.Dean;
import com.entities.DepartmentChair;
import com.entities.DepartmentStaff;
import com.entities.Instructor;
import com.entities.TA;
import com.entities.User;
import com.security.CustomUserDetails;
import com.services.AdminsService;
import com.services.DeanService;
import com.services.DepartmentChairService;
import com.services.DepartmentStaffService;
import com.services.InstructorService;
import com.services.TAService;
import com.services.UserService;

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
            admin.getFirstName(),
            admin.getLastName(),
            admin.getEmail(),
            admin.getPassword()
        );
        
    } else if (user instanceof TA) {
        TA ta = (TA) user;
        return taService.createTA(
            ta.getFirstName(),
            ta.getLastName(),
            ta.getEmail(),
            ta.getUsername(),
            ta.getPassword(),
            ta.getMaster(),
            ta.getAdvisorID(),
            ta.getPartTime(),
            ta.getDepartmentCode()
        );
    } else if (user instanceof Instructor) {
        Instructor instructor = (Instructor) user;
        return instructorService.createInstructor(
            instructor.getFirstName(),
            instructor.getLastName(),
            instructor.getEmail(),
            instructor.getUsername(),
            instructor.getPassword(),
            instructor.getDepartmentCode(),
            instructor.getTaIDs()
        );
    } else if (user instanceof DepartmentStaff) {
        DepartmentStaff departmentStaff = (DepartmentStaff) user;
        return departmentStaffService.createDepartmentStaff(
            departmentStaff.getFirstName(),
            departmentStaff.getLastName(),
            departmentStaff.getEmail(),
            departmentStaff.getUsername(),
            departmentStaff.getPassword(),
            departmentStaff.getDepartmentCode()
        );
    } else if (user instanceof DepartmentChair) {
        DepartmentChair departmentChair = (DepartmentChair) user;
        return departmentChairService.createDepartmentChair(
            departmentChair.getFirstName(),
            departmentChair.getLastName(),
            departmentChair.getEmail(),
            departmentChair.getUsername(),
            departmentChair.getPassword(),
            departmentChair.getDepartmentCode()
        );

    } else if (user instanceof Dean) {
        Dean dean = (Dean) user;
        return deanService.createDean(
            dean.getFirstName(),
            dean.getLastName(),
            dean.getEmail(),
            dean.getUsername(),
            dean.getPassword(),
            dean.getDepartmentCode()
        );
        
    } else {
        // fallback or throw error
        return userService.createUser(
            user.getFirstName(),
            user.getLastName(),
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



    /*@GetMapping("/user-info")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal CustomUserDetails user) {
            if (user == null) {
        return ResponseEntity.status(401).body("User is not authenticated");
    }
        return ResponseEntity.ok(Map.of(
            "id", user.getId(),
            "name", user.getName(),
            "email", user.getEmail(),
            "username", user.getUsername(),
            "roles", user.getAuthorities()
        ));
    }*/
    
   /* @GetMapping("/user-info")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal CustomUserDetails user) {
        System.out.println("ehe");
    if (user == null) {
        System.out.println("ehe");
        // Debugging: Check if the security context contains the user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("Authentication Principal: " + authentication.getPrincipal());
        }
        System.out.println("User is null ehe");
        return ResponseEntity.status(401).body("User is not authenticated");
    }
    return ResponseEntity.ok(Map.of(
        "id", user.getId(),
        "name", user.getName(),
        "email", user.getEmail(),
        "username", user.getUsername(),
        "roles", user.getAuthorities()
    ));
}*/


/*@GetMapping("/user-info")
public ResponseEntity<?> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
        return ResponseEntity.status(401).body("User is not authenticated");
    }
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
    return ResponseEntity.ok(Map.of(
        "id", user.getId(),
        "name", user.getName(),
        "email", user.getEmail(),
        "username", user.getUsername(),
        "roles", user.getAuthorities()
    ));
}*/

/*@GetMapping("/user-info")
public ResponseEntity<?> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
        System.out.println("Authentication is null");
        return ResponseEntity.status(401).body("User is not authenticated");
    }
    System.out.println("Authentication Principal: " + authentication.getPrincipal());
    System.out.println("Principal Class: " + authentication.getPrincipal().getClass());
    if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
        return ResponseEntity.status(401).body("User is not authenticated");
    }
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
    return ResponseEntity.ok(Map.of(
        "id", user.getId(),
        "name", user.getName(),
        "email", user.getEmail(),
        "username", user.getUsername(),
        "roles", user.getAuthorities()
    ));
}*/


/*@GetMapping("/api/user-info")
public ResponseEntity<?> getUserInfo(HttpSession session) {
    Integer userId = (Integer) session.getAttribute("userId");

    System.out.println("Session userId: " + userId);  // log for debug
    if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
    }
    // continue logic
    return ResponseEntity.ok("User ID: " + userId);
}*/

 /*  @GetMapping("/user-info")
    public Map<String, Object> getUserInfo(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        Object userId = session.getAttribute("userId");
        Object username = session.getAttribute("username");
        Object role = session.getAttribute("role");

        if (userId == null || username == null || role == null) {
            response.put("authenticated", false);
            response.put("message", "No active session or user not logged in.");
        } else {
            response.put("authenticated", true);
            response.put("userId", userId);
            response.put("username", username);
            response.put("role", role);
        }

        return response;
    }*/


    /*@GetMapping("/user-info")
public ResponseEntity<?> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
        System.out.println("Authentication is null");
        return ResponseEntity.status(401).body("User is not authenticated");
    }
    System.out.println("Authentication Principal: " + authentication.getPrincipal());
    System.out.println("Principal Class: " + authentication.getPrincipal().getClass());
    if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
        return ResponseEntity.status(401).body("User is not authenticated");
    }
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
    return ResponseEntity.ok(Map.of(
        "id", user.getId(),
        "name", user.getName(),
        "email", user.getEmail(),
        "username", user.getUsername(),
        "roles", user.getAuthorities()
    ));
}*/

/*
@GetMapping("/user-info")
public ResponseEntity<?> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
        System.out.println("Authentication is null");
        return ResponseEntity.status(401).body("User is not authenticated");
    }
    System.out.println("Authentication Principal: " + authentication.getPrincipal());
    System.out.println("Principal Class: " + authentication.getPrincipal().getClass());
    if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
        return ResponseEntity.status(401).body("User is not authenticated");
    }
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
    return ResponseEntity.ok(Map.of(
        "id", user.getId(),
        "name", user.getFirstName(),
        "surname", user.getLastName(),
        "email", user.getEmail(),
        "username", user.getUsername(),
        "roles", user.getAuthorities()
    ));
}*/



@GetMapping("/user-info")
public ResponseEntity<?> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
        System.out.println("Authentication is null");
        return ResponseEntity.status(401).body("User is not authenticated");
    }
    
    System.out.println("Authentication Principal: " + authentication.getPrincipal());
    System.out.println("Principal Class: " + authentication.getPrincipal().getClass());
    
    if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
        return ResponseEntity.status(401).body("User is not authenticated");
    }
    
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
    
    // Create map with null checks
    Map<String, Object> userInfo = new HashMap<>();
    if (user.getId() != null) userInfo.put("id", user.getId());
    if (user.getFirstName() != null) userInfo.put("firstName", user.getFirstName());
    if (user.getLastName() != null) userInfo.put("lastName", user.getLastName());
    if (user.getEmail() != null) userInfo.put("email", user.getEmail());
    if (user.getUsername() != null) userInfo.put("username", user.getUsername());
    if (user.getAuthorities() != null) userInfo.put("roles", user.getAuthorities());
    
    return ResponseEntity.ok(userInfo);
}

@GetMapping("/userN")
public Integer getUserId(  @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
    if( firstName == null || lastName == null) {
        return null;
    } else {

        int id1= userService.getUserIdByFirstname(firstName).getId();
        int id2= userService.getUserIdByLastname(lastName).getId();
        
        if (id1 == id2) {
            System.out.println("User ID: " + id1);
            return id1;
        } else {
            System.out.println("User ID not found or mismatch");
            return null;
        }
    }
    }

}
