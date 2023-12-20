package com.civiliansconnection.capp.service;

import com.civiliansconnection.capp.domain.User;
import com.civiliansconnection.capp.repository.UserRepository;

import com.civiliansconnection.capp.security.CivUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CivUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CivUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
        UserDetails userDetails = new CivUserDetails(user);
        return userDetails;
    }
}
