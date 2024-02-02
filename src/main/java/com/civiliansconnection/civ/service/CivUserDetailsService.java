package com.civiliansconnection.civ.service;

import com.civiliansconnection.civ.domain.User;
import com.civiliansconnection.civ.repository.UserRepository;

import com.civiliansconnection.civ.security.CivUserDetails;
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
    public CivUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
        CivUserDetails userDetails = new CivUserDetails(user);
        return userDetails;
    }
}
