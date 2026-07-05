package com.mamahealth.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mamahealth.entity.User;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * Return the original User entity.
     */
    public User getUser() {
        return user;
    }

    /**
     * User role (ROLE_MOTHER, ROLE_DOCTOR, ROLE_ADMIN)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }

    /**
     * Spring Security uses this as the username.
     * We use email.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Hashed password from the database.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Account is not expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Account is not locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Credentials are valid.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * User account is enabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}