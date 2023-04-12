package com.example.springboot_vue.security.pojo;

import com.example.springboot_vue.pojo.user.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
public class JwtUserDetails implements UserDetails {

    private String username;

    private User user;

    private List<GrantedAuthority> list;

    private String token;

    public JwtUserDetails(User user, List<GrantedAuthority> list, String token) {
        this.user = user;
        this.list = list;
        this.token = token;
    }

    public JwtUserDetails() {

    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return list;
    }

    @Override
    public String getPassword() {
        return new BCryptPasswordEncoder().encode(user.getPassword());
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        JwtUserDetails user = (JwtUserDetails) o;
//        return Objects.equals(username, user.getUsername());
        return this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return username;
    }
}
