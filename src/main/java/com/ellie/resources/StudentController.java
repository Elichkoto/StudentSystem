package com.ellie.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ellie.dto.StudentDTO;
import com.ellie.service.StudentService;

@RestController
@RequestMapping(value = "/api")
public class StudentController
{
    @Autowired
    private StudentService studentService;

    @CrossOrigin
    @RequestMapping(value = "/students", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO)
    {
        if (studentService.getStudent(studentDTO.getId()) != null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        studentService.saveStudent(studentDTO);
        return new ResponseEntity<StudentDTO>(studentDTO, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ResponseEntity<?> getAllStudents()
    {
    	List<StudentDTO> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStudent(@PathVariable Long id)
    {
        if (id == 1)
        {
            createAdmin(id);
        }
        StudentDTO studentDTO = studentService.getStudent(id);
        if (studentDTO == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<StudentDTO>(studentDTO, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/delete/student/{id}", method = RequestMethod.DELETE)
    public void deleteStudent(@PathVariable Long id)
    {
        studentService.deleteStudent(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/students", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudent(@RequestBody StudentDTO studentDTO)
    {
        studentService.saveStudent(studentDTO);
        return new ResponseEntity<StudentDTO>(studentDTO, HttpStatus.OK);
    }

    private void createAdmin(Long id)
    {
        if (studentService.getStudent(id) == null)
        {
            StudentDTO student = new StudentDTO();
            student.setId(id);
            student.setFirstName("admin");
            student.setLastName("admin");
            student.setPersonalData("Teacher");
            createStudent(student);
        }
    }
}
