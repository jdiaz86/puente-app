package com.puente.puenteapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.util.PuenteException;
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
	
	@RequestMapping(method = POST, value = "/new")
    public CourseEnrollment create(@RequestBody CourseEnrollment courseEnrollment) {
        return repository.save(courseEnrollment);
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<CourseEnrollment> update(@PathVariable(value = "id") Integer id, @RequestBody CourseEnrollment dto) throws PuenteException {
    	CourseEnrollment courseEnrollment = getById(repository, id);
        if (courseEnrollment == null) {
            return ResponseEntity.notFound().build();
        }
        copy(dto, courseEnrollment);
        return ResponseEntity.ok(repository.save(courseEnrollment));
    }

}
