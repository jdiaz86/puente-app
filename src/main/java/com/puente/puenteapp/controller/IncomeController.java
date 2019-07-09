package com.puente.puenteapp.controller;

import com.puente.puenteapp.model.entity.Income;
import com.puente.puenteapp.model.repository.IncomeRepository;
import com.puente.puenteapp.util.PuenteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController extends BaseController {
    
    @Autowired
    private IncomeRepository repository;
    
    @GetMapping("/")
    public List<Income> getAll() {
        return repository.findAll();
    }
    
    @GetMapping("/list/{id}")
    public List<Income> getAll(@PathVariable(value = "id") Integer id) {
        return repository.findAllById(Arrays.asList(id));
    }
    
    @GetMapping("/{id}")
    public Income get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(repository, id);
    }
    
    @RequestMapping(method = POST, value = "/new")
    public Income create(@RequestBody Income income) {
        return repository.save(income);
    }
    
    @RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<Income> update(@PathVariable(value = "id") Integer id, @RequestBody Income dto) throws PuenteException {
        Income income = getById(repository, id);
        if (income == null) {
            return ResponseEntity.notFound().build();
        }
        copy(dto, income);
        return ResponseEntity.ok(repository.save(income));
    }
    
}
