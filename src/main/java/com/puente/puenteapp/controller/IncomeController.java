package com.puente.puenteapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.controller.util.PuenteException;
import com.puente.puenteapp.model.entity.Income;
import com.puente.puenteapp.model.repository.IncomeRepository;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController extends BaseController {
	
	@Autowired
    private IncomeRepository repository;
	
	@GetMapping("/")
    public List<Income> getAll() {
        return repository.findAll();
    }
	
	@GetMapping("/{id}")
    public Income get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(repository, id);
    }

}
