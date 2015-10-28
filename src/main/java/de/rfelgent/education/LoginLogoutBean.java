package de.rfelgent.education;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * This is an annotation based approach for defining jsf managed beans.
 * Alternatively, you could define managed beans in "faces-config.xml"
 */
@ManagedBean
@SessionScoped
public class LoginLogoutBean implements Serializable {

    private String username;
    private String password;

    public String login() {
        if (username.equals("max")
            && password.equals("123456")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login successful", null));
            //navigation rule defined in faces-config.xml
            return "success";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
        //jsf fallback to stay on the same page!
        return null;
    }

    public String logout() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logout successful", null));
        //control navigation programmatically (without faces-config.xml)
        return "/logout.xhtml?faces-redirect=true";
    }
    /////////////////
    //GETTER/SETTER
    /////////////////

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
