package com.puente.puenteapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puente.puenteapp.util.PuenteException;
import com.puente.puenteapp.model.entity.Outcome;
import com.puente.puenteapp.model.entity.Status;
import com.puente.puenteapp.model.entity.User;
import com.puente.puenteapp.model.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController {
	
	@Autowired
    private UserRepository repository;
	
	@GetMapping("/")
    public List<User> getAll() {
        return repository.findAll();
    }
	
	@GetMapping("/decode/{decoded}")
    public String get(@PathVariable(value = "decoded") String decoded) throws PuenteException {
		return new BCryptPasswordEncoder().encode(decoded);
    }
	
	@GetMapping("/{id}")
    public User get(@PathVariable(value = "id") Integer id) throws PuenteException {
        return getById(repository, id);
    }
	
	@RequestMapping(method = PUT, value = "/{id}")
    public ResponseEntity<User> update(@PathVariable(value = "id") Integer id, @RequestBody User dto) throws PuenteException {
		User user = getById(repository, id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        copy(dto, user);
        return ResponseEntity.ok(repository.save(user));
    }
	
	@PutMapping(value = "/validateUser/{id}")
    public ResponseEntity<String> updateAddress(@PathVariable("id") final Integer id) throws PuenteException {
        User user = getById(repository, id);
        user.setStatus(Status.ACTIVE);
        
        repository.save(user);
        return ResponseEntity.ok(Status.ACTIVE.getName());
    }
	
	@GetMapping("/me")
    public User getUserObj(Principal principal) throws PuenteException {
        Integer userId = ((User) ((OAuth2Authentication) principal).getPrincipal()).getId();

        return getById(repository, userId);
    }


}
