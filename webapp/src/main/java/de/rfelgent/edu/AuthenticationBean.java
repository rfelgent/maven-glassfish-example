package de.rfelgent.edu;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

// This is an annotation based approach for defining jsf managed beans.
// Alternatively, you could define managed beans in "faces-config.xml"
@ManagedBean
@RequestScoped
public class AuthenticationBean implements Serializable {

    private String username;
    private String password;

    private String redirectUrl;

    public AuthenticationBean() {
        // the not authenticated user navigates to a protected area ==> GF triggers RequestDispatcher for presenting the login form
        // the RequestDispatcher saves the original invoked URL in the request. This information is used for later redirect performed by this bean!
        final String forwardedRequestUri = (String)getCurrentInstance().getExternalContext().getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        if (forwardedRequestUri != null && forwardedRequestUri.trim().length() > 0) {
            redirectUrl = forwardedRequestUri;
            //append the query params, if there are any, to the redirect url
            final String queryString = (String)getExternalContext().getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);
            if (queryString != null && queryString.trim().length()>0) {
                redirectUrl += ("?" + queryString);
            }
        } else {
            //bean was used by login.xhtml for submitting the credentials
        }
    }

    public String logout() {
        try {
            //the later "invalidateSession()" does not perform a logout, although the user is defacto not known anymore due to discarded session
            ((HttpServletRequest)getExternalContext().getRequest()).logout();
        } catch (ServletException e) {
            //logout failed... continue anyway
        }
        getExternalContext().invalidateSession();
        getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Logout successful", null));
        //the JSF messages must survive the redirect!
        getExternalContext().getFlash().setKeepMessages(true);
        //programmatic redirect by JSF, but without faces-config.xml
        return "/logout.xhtml?faces-redirect=true";
    }

    public String login() {
        HttpServletRequest request = (HttpServletRequest) getExternalContext().getRequest();
        //is there i a logged-in user?
        if (request.getUserPrincipal() != null) {
            try {
                //instead of silently logout, a redirect to protected area might be possible as well
                request.logout();
            } catch (ServletException e) {
                //swallow
            }
        }

        //the JSF messages must survive the redirect!
        getExternalContext().getFlash().setKeepMessages(true);
        try {
            request.login(getUsername(), getPassword());
            getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You are logged in as '" + request.getUserPrincipal().getName() + "'", null));
            //programmatic redirect by Servlet Api (without JSF)
            getExternalContext().redirect(getRedirectUrl());
            getCurrentInstance().responseComplete();
        } catch (ServletException e) {
            //inspect the exception for more details (e.g. wrong credentials)
            getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
        } catch (IOException e) {
            //should not happen
            getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Redirect failed", null));
        }
        //value "null" sets a navigation rule like "stay on the current view/page"
        return null;
    }

    private FacesContext getCurrentInstance() {
        return FacesContext.getCurrentInstance();
    }

    private ExternalContext getExternalContext() {
        return getCurrentInstance().getExternalContext();
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
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
}
