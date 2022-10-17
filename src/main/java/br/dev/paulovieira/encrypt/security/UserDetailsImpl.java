package br.dev.paulovieira.encrypt.security;

import br.dev.paulovieira.encrypt.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UserDetailsImpl implements UserDetails {

    private final Optional<UserModel> user;

    public UserDetailsImpl(Optional<UserModel> user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return user.orElse(new UserModel()).getPassword();
    }

    @Override
    public String getUsername() {
        return user.orElse(new UserModel()).getLogin();
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
