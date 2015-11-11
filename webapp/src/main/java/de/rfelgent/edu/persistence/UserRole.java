package de.rfelgent.edu.persistence;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "demo_userrole")
public class UserRole extends AbstractEntity {


    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name="fk_user_id")
    private User user;

    public enum Role {
        ADMIN,
        USER;
    }

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
