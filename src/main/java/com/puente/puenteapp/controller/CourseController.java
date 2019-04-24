package com.puente.puenteapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.controller.util.PuenteException;
import com.puente.puenteapp.model.entity.Course;
import com.puente.puenteapp.model.repository.CourseRepository;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
	
	@Autowired
    private CourseRepository courseRepository;
	
	@GetMapping("/")
    public List<Course> getAll() {
        return courseRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Course get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return courseRepository.findById(id).orElseThrow(() -> new PuenteException("Id not found: " + id));
    }

}
