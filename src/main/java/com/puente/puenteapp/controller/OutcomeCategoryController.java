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
import com.puente.puenteapp.model.entity.OutcomeCategory;
import com.puente.puenteapp.model.repository.OutcomeCategoryRepository;


@RestController
@RequestMapping("/api/outcomeCategorys")
public class OutcomeCategoryController extends BaseController {
	
	@Autowired
    private OutcomeCategoryRepository repository;
	
	@GetMapping("/")
    public List<OutcomeCategory> getAll() {
        return repository.findAll();
    }
	
	@GetMapping("/{id}")
    public OutcomeCategory get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(repository, id);
    }
	
	@RequestMapping(method = POST, value = "/new")
    public OutcomeCategory create(@RequestBody OutcomeCategory outcomeCategory) {
        return repository.save(outcomeCategory);
    }

    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<OutcomeCategory> update(@PathVariable(value = "id") Integer id, @RequestBody OutcomeCategory dto) throws PuenteException {
    	OutcomeCategory outcomeCategory = getById(repository, id);
        if (outcomeCategory == null) {
            return ResponseEntity.notFound().build();
        }
        copy(dto, outcomeCategory);
        return ResponseEntity.ok(repository.save(outcomeCategory));
    }

}
