package com.puente.puenteapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.controller.util.PuenteException;
import com.puente.puenteapp.model.entity.Outcome;
import com.puente.puenteapp.model.repository.OutcomeRepository;

@RestController
@RequestMapping("/api/outcomes")
public class OutcomeController extends BaseController {
	
	@Autowired
    private OutcomeRepository repository;
	
	@GetMapping("/")
    public List<Outcome> getAll() {
        return repository.findAll();
    }
	
	@GetMapping("/{id}")
    public Outcome get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(repository, id);
    }

}
