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
import com.puente.puenteapp.model.entity.Course;
import com.puente.puenteapp.model.repository.CourseRepository;

@RestController
@RequestMapping("/api/courses")
public class CourseController extends BaseController {
	
	@Autowired
    private CourseRepository repository;
	
	@GetMapping("/")
    public List<Course> getAll() {
        return repository.findAll();
    }
	
	@GetMapping("/{id}")
    public Course get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(repository, id);
    }
	
	@RequestMapping(method = POST, value = "/new")
    public Course create(@RequestBody Course course) {
        return repository.save(course);
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Course> update(@PathVariable(value = "id") Integer id, @RequestBody Course dto) throws PuenteException {
    	Course course = getById(repository, id);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        copy(dto, course);
        return ResponseEntity.ok(repository.save(course));
    }

}
