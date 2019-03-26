package com.puente.puenteapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.controller.util.PuenteException;
import com.puente.puenteapp.model.entity.Role;
import com.puente.puenteapp.model.repository.RoleRepository;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
	
	@Autowired
    private RoleRepository roleRepository;
	
	@GetMapping("/")
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Role get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return roleRepository.findById(id).orElseThrow(() -> new PuenteException("Id not found: " + id));
    }

}
