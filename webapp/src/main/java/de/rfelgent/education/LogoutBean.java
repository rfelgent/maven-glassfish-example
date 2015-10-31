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
public class LogoutBean implements Serializable {

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logout successful", null));
        //control navigation programmatically (without faces-config.xml)
        return "/logout.xhtml?faces-redirect=true";
    }
}
