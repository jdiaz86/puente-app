package com.puente.puenteapp.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String username;
	
	private String lastName;
	
	private String firstName;
	
	private String email;
	
	private String password;
	
	private String country;
	
	private String phone;
	
	private String foreign;
	
	private String taxCorrelative;
	
	private String dni;
	
	@LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private List<Role> roles;
	
	@ManyToOne
    private Status status;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (!CollectionUtils.isEmpty(getRoles())) {
            return getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
