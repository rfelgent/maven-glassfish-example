package de.rfelgent.edu.persistence;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "demo_user")
public class User extends AbstractEntity {

    @Column(unique = true, nullable = false, length = 20)
    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @Column(nullable = false, length = 20)
    @NotNull
    @Size(min = 1, max = 20)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Size(min = 1)
    private Set<UserRole> roles = new HashSet<>();

    public User() {
        this(null, null);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
