package com.puente.puenteapp.model.repository;

import com.puente.puenteapp.model.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findTopByUsername(String username);
    
	User findTopByEmail(String email);
    
    List<User> findAllByFirstNameLike(String firstName);

}
