package com.example.swd392.model;

import com.example.swd392.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsersID")
    private int usersID;
    private String email;
    private String name;
    private String password;

//    @Column(name = "Email", nullable = false)
//    private String email;
//
//    @Column(name = "Name", nullable = false)
//    private String name;
//
//    @Column(name = "Password", nullable = false)
//    private String password;
//
//    @Column(name = "Address")
//    private String address;
//
//    @Column(name = "Phone")
//    private String phone;
//
//    @Column(name = "Status")
//    private Boolean status;
//
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy ="user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
