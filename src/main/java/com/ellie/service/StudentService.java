package com.ellie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ellie.dto.StudentDTO;
import com.ellie.entity.Student;
import com.ellie.helper.StudentTransformation;
import com.ellie.repository.StudentRepository;

@Service
@Component
@Transactional
public class StudentService
{
    @Autowired
    private StudentRepository studentRepository;

    public void saveStudent(StudentDTO studentDTO)
    {
        Student student = StudentTransformation.transformStudentDtoToStudent(studentDTO);
        studentRepository.save(student);
    }

    public List<StudentDTO> findAllStudents()
    {
        List<Student> students = studentRepository.findAll();

        return transformStudents(students);
    }

    public StudentDTO getStudent(Long id)
    {
        Student student = studentRepository.findById(id);
        if (student == null)
        {
            return null;
        }

        StudentDTO studentDTO = StudentTransformation.transformStudentToStudentDTO(student);
        return studentDTO;
    }

    public void deleteStudent(Long id)
    {
        studentRepository.delete(id);
    }

    private List<StudentDTO> transformStudents(List<Student> students)
    {
        List<StudentDTO> studentDTOS = new ArrayList();
        for (Student student : students)
        {
            studentDTOS.add(StudentTransformation.transformStudentToStudentDTO(student));
        }
        return studentDTOS;
    }
}
