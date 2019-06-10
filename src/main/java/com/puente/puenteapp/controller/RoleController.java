package com.puente.puenteapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.util.PuenteException;
import com.puente.puenteapp.model.entity.Role;
import com.puente.puenteapp.model.repository.RoleRepository;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends BaseController {
	
	@Autowired
    private RoleRepository repository;
	
	@GetMapping("/")
    public List<Role> getAll() {
        return repository.findAll();
    }
	
	@GetMapping("/{id}")
    public Role get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(repository, id);
    }

}
