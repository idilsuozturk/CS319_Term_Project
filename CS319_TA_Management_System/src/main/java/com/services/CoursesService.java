package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.Course;
import com.entities.Instructor;
import com.entities.TA;
import com.repositories.CoursesRepository;

@Service
public class CoursesService {

    private final CoursesRepository courseRepository;

    private final InstructorService instructorService;

    private final TAService taService;

    public CoursesService(CoursesRepository courseRepository, InstructorService instructorService, TAService taService) {
        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
        this.taService = taService;
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();  
    }   

    public Course createCourse(String code, String section, Integer instructorID, ArrayList<Integer> taIDs, String[] schedule, boolean masterphd) {
        return courseRepository.save(new Course(code, section, instructorID, taIDs, schedule, masterphd));  
    }

 
    public void deleteCourseByID(Integer id) {
        courseRepository.deleteById(id);  
    }

    public Course updateCourseByID(Integer id, Course course) {
        Course existingCourse = courseRepository.findById(id).orElse(null);  
        if (existingCourse != null) {
            existingCourse.setCode(course.getCode());
            existingCourse.setSection(course.getSection());
            existingCourse.setInstructorID(course.getInstructorID());
            existingCourse.setTaIDs(course.getTaIDs());
            existingCourse.setSchedule(course.getSchedule());
            existingCourse.setMasterphd(course.getMasterphd());
            return courseRepository.save(existingCourse);  
        }
        return null;  
    }

    public Course getCourseByID(Integer id) {
        return courseRepository.findById(id).orElse(null);  
    }

    public Course getCourseByCodeAndSection(String code, String section){
        return courseRepository.findByCodeAndSection(code, section).orElse(null);
    }

    public Course getCourseByCode(String code){
        return courseRepository.findFirstByCode(code).orElse(null);
    }

    public boolean addCourse(Integer courseID, Integer taID, boolean taken){
        if (taken){
            Course course = getCourseByID(courseID);
            if (course != null){
                TA ta = taService.getTAByID(taID);
                if (ta != null){
                    String[] courseSchedule = course.getSchedule();
                    String[] taSchedule = ta.getSchedule();
                    for (int i = 0; i < 98; i++){
                        if (courseSchedule[i] != null){
                            if (taSchedule[i] != null){
                                return false;
                            }
                            else {
                                taSchedule[i] = courseSchedule[i] + course.getCode() + " - " + course.getSection();
                            }
                        }
                    }
                    ArrayList<Integer> coursesTaken = ta.getCoursesTaken();
                    coursesTaken.add(courseID);
                    ta.setCoursesTaken(coursesTaken);
                    taService.updateTAByID(taID, ta);
                    return true;
                }
                return false;
            }
            return false;
        }
        else {
            Course course = getCourseByID(courseID);
            if (course != null){
                TA ta = taService.getTAByID(taID);
                if (ta != null){
                    ArrayList<Integer> coursesAssisted = ta.getCoursesAssisted();
                    for (int i : coursesAssisted){
                        if (i == courseID){
                            return false;
                        }
                    }
                    coursesAssisted.add(courseID);
                    ta.setCoursesAssisted(coursesAssisted);
                    taService.updateTAByID(taID, ta);
                    Instructor instructor = instructorService.getInstructorByID(course.getInstructorID());
                    if (instructor != null){
                        ArrayList<Integer> taIDs = instructor.getTaIDs();
                        taIDs.add(taID);
                        instructor.setTaIDs(taIDs);
                        instructorService.updateInstructorByID(instructor.getId(), instructor);
                    }
                    return true;
                }
                return false;
            }
            return false;
        }
    }

    public List<Integer> viewCoursesGiven(Integer id){
        Instructor instructor = instructorService.getInstructorByID(id);
        if (instructor == null){
            return null;
        }

        List<Integer> output = new ArrayList<>();
        /*for (int i : instructor.getCourseIDs()){
            System.out.println(i);
        }*/
        System.out.println("CHECK");
        System.out.println(instructor.getCourseIDs().size());
        for (int i : instructor.getCourseIDs()){
            Course course = getCourseByID(i);
            System.out.println(course.getCode());
            System.out.println("BURAYA GELİYOR MU");
            output.add(i);
        }
        for (int i : output){
            System.out.println(i);
        }
        return output;
    }

    public List<String> viewAssistingTAs(Integer id){
        Course course = getCourseByID(id);
        if (course == null){
            return null;
        }
        List<String> output = new ArrayList<>();
        for (int i : course.getTaIDs()){
            TA ta = taService.getTAByID(i);
            if (ta == null) {
                continue;
            }
            output.add(convertTAToString(ta));
        }
        return output;
    }

    public List<String> viewCoursesTaken(Integer id){
        TA ta = taService.getTAByID(id);
        if (ta == null){
            return null;
        }
        List<String> output = new ArrayList<>();
        for (int i : ta.getCoursesTaken()){
            Course course = getCourseByID(i);
            if (course == null){
                continue;
            }
            output.add(convertCourseToString(course, true));
        }
        return output;
    }

    public List<String> viewCoursesAssisted(Integer id){
        TA ta = taService.getTAByID(id);
        if (ta == null){
            return null;
        }
        List<String> output = new ArrayList<>();
        for (int i : ta.getCoursesAssisted()){
            Course course = getCourseByID(i);
            if (course == null){
                continue;
            }
            output.add(convertCourseToString(course, false));
        }
        return output;
    }

    private String convertCourseToString(Course course, boolean taken) {
        String newString = "Course Information:\nCourse Name: " + course.getCode();
        if (taken){
            newString += "\nCourse Section: " + course.getSection() + " (for courses schedule, please view your schedule)";
        }
        newString += "\nCourse Level: ";
        if (course.getMasterphd()){
            newString += " Master/PHD Level";
        }
        if (!taken){
            Instructor instructor = instructorService.getInstructorByID(course.getInstructorID());
            newString += "\nInstructor: " + instructor.getName();
        }
        return newString;
    }

    private String convertTAToString(TA ta) {
        Instructor instructor = instructorService.getInstructorByID(ta.getAdvisorID());
        String newString = "TA Name: " + ta.getFirstName() + "\nTA Surname: " + ta.getLastName() + "\nTA ID: " + ta.getUsername() +
        "TA Email: " + ta.getEmail() + "TA's Advisor: ";
        if (instructor != null) {
            newString += instructor.getName();
        }  
        newString += "\nThe Courses TA is assigned to: ";
        for (int i : ta.getCoursesAssisted()){
            Course course = getCourseByID(i);
            if (course == null) {
                continue;
            }
            newString += "\t" + course.getCode();
        }
        newString += "\nThe Courses TA is taking: ";
        for (int i : ta.getCoursesTaken()){
            Course course = getCourseByID(i);
            if (course == null) {
                continue;
            }
            newString += "\t" + course.getCode() + " - " + course.getSection();
        }
        newString += "\nTA's Workload: " + ta.getTotalWorkload();
        return newString;
    }
}
