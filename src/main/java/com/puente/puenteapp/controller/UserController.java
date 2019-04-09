package com.puente.puenteapp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.controller.util.PuenteException;
import com.puente.puenteapp.model.entity.Status;
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
	
	@GetMapping("/decode/{decoded}")
    public String get(@PathVariable(value = "decoded") String decoded) throws PuenteException {
		return new BCryptPasswordEncoder().encode(decoded);
    }
	
	@GetMapping("/{id}")
    public User get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(id);
    }
	
	@PutMapping(value = "/validateUser/{id}")
    public ResponseEntity<String> updateAddress(@PathVariable("id") final Integer id) throws PuenteException {
        User user = getById(id);
        user.setStatus(Status.ACTIVE);
        
        userRepository.save(user);
        return ResponseEntity.ok(Status.ACTIVE.getName());
    }
	
	@GetMapping("/me")
    public User getUserObj(Principal principal) throws PuenteException {
        Integer userId = ((User) ((OAuth2Authentication) principal).getPrincipal()).getId();

        return getById(userId);
    }
	
	private User getById(Integer id) throws PuenteException {
		return userRepository.findById(id).orElseThrow(() -> new PuenteException("Id not found: " + id));
	}

}
