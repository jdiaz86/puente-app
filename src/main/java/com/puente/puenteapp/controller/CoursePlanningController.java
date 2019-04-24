package com.puente.puenteapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.controller.util.PuenteException;
import com.puente.puenteapp.model.entity.CoursePlanning;
import com.puente.puenteapp.model.repository.CoursePlanningRepository;

@RestController
@RequestMapping("/api/coursePlannings")
public class CoursePlanningController extends BaseController {
	
	@Autowired
    private CoursePlanningRepository repository;
	
	@GetMapping("/")
    public List<CoursePlanning> getAll() {
        return repository.findAll();
    }
	
	@GetMapping("/{id}")
    public CoursePlanning get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(repository, id);
    }

}
