package com.puente.puenteapp.model.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.puente.puenteapp.model.entity.Status;
import com.puente.puenteapp.model.entity.User;
import com.puente.puenteapp.model.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String searchText) throws AuthenticationException {
        User user = userRepository.findTopByUsername(searchText);

        if(user == null){
            user = userRepository.findTopByEmail(searchText);
        }

        if (null == user) {
            LOG.info("Username not found");
            throw new UsernameNotFoundException("Username" + searchText + " not found");
        } else if (Objects.equals(Status.INVITED, user.getStatus())){
            LOG.info("User found but is pending with username: " + searchText);
            throw new LockedException("Username " + searchText + " is pending");
        } else {
            LOG.info("User found with username: " + searchText);
        }

        return user;
    }

}
