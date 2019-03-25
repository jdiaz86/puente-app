package com.puente.puenteapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.puente.puenteapp.model.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
