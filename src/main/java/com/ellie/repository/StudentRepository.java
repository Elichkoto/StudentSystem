package com.ellie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ellie.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findById(Long id);
}
