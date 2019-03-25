package com.puente.puenteapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.puente.puenteapp.model.entity.Role;
import com.puente.puenteapp.model.repository.RoleRepository;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
	
	@Autowired
    private RoleRepository roleRepository;
	
	@GetMapping("/roles")
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

}
