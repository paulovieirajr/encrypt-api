package br.dev.paulovieira.encrypt.security.service;

import br.dev.paulovieira.encrypt.repository.UserRepository;
import br.dev.paulovieira.encrypt.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username)
                .map(u -> new UserDetailsImpl(Optional.of(u)))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
