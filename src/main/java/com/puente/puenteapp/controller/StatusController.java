package com.puente.puenteapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.util.PuenteException;
import com.puente.puenteapp.model.entity.Status;
import com.puente.puenteapp.model.repository.StatusRepository;

@RestController
@RequestMapping("/api/status")
public class StatusController extends BaseController {
	
	@Autowired
    private StatusRepository repository;
	
	@GetMapping("/")
    public List<Status> getAll() {
        return repository.findAll();
    }
	
	@GetMapping("/{id}")
    public Status get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(repository, id);
    }

}
