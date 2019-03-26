package com.puente.puenteapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.controller.util.PuenteException;
import com.puente.puenteapp.model.entity.User;
import com.puente.puenteapp.model.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
    private UserRepository userRepository;
	
	@GetMapping("/")
    public List<User> getAll() {
        return userRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public User get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return userRepository.findById(id).orElseThrow(() -> new PuenteException("Id not found: " + id));
    }

}
