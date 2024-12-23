package com.movie.app.service;

import com.movie.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DomainUserDetailService implements UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(DomainUserDetailService.class);

    private final UserRepository userRepository;


    public DomainUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Authenticating by, {}", username);


        return userRepository.findByUsername(username)
                .map(user -> {

                    if (!user.getIsActive().equals(true)) {
                        throw new UsernameNotFoundException("User with login: " + username + " was not activated");
                    }

                    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

                    if(user.getIsAdmin()) {
                        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    } else {
                        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    }

                    return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User: " + username + "not found"));


    }
}
