package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.entities.Classroom;
import com.repositories.ClassroomRepository;

@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository){
        this.classroomRepository = classroomRepository;
    }

    public List<Classroom> getAllClassrooms(){
        return classroomRepository.findAll();
    }

    public Classroom createClassroom(String classroomName, int capacity, int examCapacity, String[] examList){
        return classroomRepository.save(new Classroom(classroomName, capacity, examCapacity, examList));
    }

    public void deleteClassroomByID(Integer id) {
        classroomRepository.deleteById(id);  
    }

    public Classroom updateClassroomByID(Integer id, Classroom classroom) {
        Classroom existingClassroom = classroomRepository.findById(id).orElse(null);  
        if (existingClassroom != null) {
            existingClassroom.setClassroomName(classroom.getClassroomName());
            existingClassroom.setCapacity(classroom.getCapacity());
            existingClassroom.setExamCapacity(classroom.getExamCapacity());
            existingClassroom.setExamList(classroom.getExamList());
            return classroomRepository.save(existingClassroom);  
        }
        return null;  
    }

    public Classroom getClassroomByID(Integer id) {
        return classroomRepository.findById(id).orElse(null);  
    }
}
