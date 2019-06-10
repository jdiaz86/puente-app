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
	
	@RequestMapping(method = POST, value = "/new")
    public Outcome create(@RequestBody Outcome outcome) {
        return repository.save(outcome);
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Outcome> update(@PathVariable(value = "id") Integer id, @RequestBody Outcome dto) throws PuenteException {
    	Outcome outcome = getById(repository, id);
        if (outcome == null) {
            return ResponseEntity.notFound().build();
        }
        copy(dto, outcome);
        return ResponseEntity.ok(repository.save(outcome));
    }

}
