package de.rfelgent.edu;

import de.rfelgent.edu.persistence.User;
import de.rfelgent.edu.persistence.UserRole;
import de.rfelgent.edu.service.UserAlreadyExistsException;
import de.rfelgent.edu.service.UserService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;


@ManagedBean
@RequestScoped
public class RegisterBean implements Serializable {

    private User user = new User();

    @EJB
    private UserService userService;

    public String register() {
        //jsf message must survive redirect
        getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        try {
            UserRole role = new UserRole();
            role.setRole(UserRole.Role.USER);
            role.setUser(user);
            user.setRoles(new HashSet<>(Arrays.asList(role)));
            userService.register(user);
            getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User registered successfully. You can login now!", null));
            return "/login.xhtml?faces-redirect=true";
        } catch (UserAlreadyExistsException e) {
            getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "User already exists", null));
        }
        return null;
    }

    private FacesContext getCurrentInstance() {
        return FacesContext.getCurrentInstance();
    }

    public User getUser() {
        return user;
    }
}
