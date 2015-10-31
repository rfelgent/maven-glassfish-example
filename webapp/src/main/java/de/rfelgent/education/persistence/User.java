package de.rfelgent.education.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "demo_user")
public class User extends AbstractEntity {

    private String username;

    private String password;



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


}
