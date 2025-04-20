 package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.TA;
// import com.entities.Task;
import com.entities.Course;
// import com.entities.Exam;
import com.repositories.TARepository;
import com.repositories.CoursesRepository;

import java.util.List;

@Service
public class TAService {
    @Autowired
    private TARepository taRepository;

    @Autowired
    private CoursesRepository coursesRepository; // not used

    public List<TA> getAllTAs() {
        return taRepository.findAll();
    }

    public TA createTA(Integer id, String email, String userName, String password, Integer[] currentAssistingCourses,
            Integer[] currentTakingCourses,

            Integer advisor, Integer totalWorkload, Integer tcNumber, Integer[] proctoringExams) {
        TA newTA = new TA(id, email, userName, password, currentAssistingCourses, currentTakingCourses, advisor,
                totalWorkload, tcNumber, proctoringExams);

        return taRepository.save(newTA);
    }

    public TA getTAById(Integer id) {

        return taRepository.findById(id).orElse(null);

    }

    public void deleteTAById(Integer id) {

        taRepository.deleteById(id);
    }

    public TA updateTA(Integer id, TA ta) {
        TA existingTA = taRepository.findById(id).orElse(null);
        if (existingTA != null) {

            existingTA.setEmail(ta.getEmail());
            existingTA.setUserName(ta.getUserName());
            existingTA.setPassword(ta.getPassword());

            existingTA.setCurrentAssistingCourses(ta.getCurrentAssistingCourses());
            existingTA.setCurrentTakingCourses(ta.getCurrentTakingCourses());
            existingTA.setAdvisor(ta.getAdvisor());

            existingTA.setTotalWorkload(ta.getTotalWorkload());
            existingTA.setTcNumber(ta.getTcNumber());
            existingTA.setProctoringExams(ta.getProctoringExams());
            return taRepository.save(existingTA);
        }
        return null;
    }

    // gotta fiugre out schedule.java
    public void viewSchedule(Integer id) {
        // TODO
    }

    public boolean sendProcSwapRequest(Integer id, Integer examId) {
        // TODO
        return true;
    }

    public boolean approveProcSwapRequest(Integer id, Integer examId) {
        //
        return true;
    }

    public boolean rejectProcSwapRequest(Integer id, Integer examId) {
        // todo
        return true;
    }

    public boolean requestLeave(Integer id, String date, String reason) {

        return true;
    }

    public boolean viewTotalWorkload(Integer id) {
        //
        TA ta = taRepository.findById(id).orElse(null);
        return ta != null;
    }

    public boolean viewProctoringAssignment(Integer id) {
        // Has to look through Exam [ ]. ;(
        TA ta = taRepository.findById(id).orElse(null);
        return ta != null;
    }

}