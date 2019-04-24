package com.puente.puenteapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.controller.util.PuenteException;
import com.puente.puenteapp.model.entity.CourseEnrollment;
import com.puente.puenteapp.model.repository.CourseEnrollmentRepository;

@RestController
@RequestMapping("/api/courseEnrollments")
public class CourseEnrollmentController extends BaseController {
	
	@Autowired
    private CourseEnrollmentRepository repository;
	
	@GetMapping("/")
    public List<CourseEnrollment> getAll() {
        return repository.findAll();
    }
	
	@GetMapping("/{id}")
    public CourseEnrollment get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(repository, id);
    }

}
