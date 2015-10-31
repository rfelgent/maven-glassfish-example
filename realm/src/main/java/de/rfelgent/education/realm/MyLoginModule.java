package de.rfelgent.education.realm;

import com.sun.enterprise.security.BasePasswordLoginModule;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;

public class MyLoginModule extends BasePasswordLoginModule {

    @Override
    protected void authenticateUser() throws LoginException {
        if (!doAuthentication()) {
            throw new LoginException("invalid username/password");
        }
        //you must call this method yourself!
        //the documentation of commitUserAuthentication() is not clear about this!
        commitUserAuthentication(getAuthenticationService().getGroups(getUsername()).toArray(new String[]{}));
    }


    protected boolean doAuthentication() {
        final String username = getUsername();
        final String password = new String(getPasswordChar());

        return getAuthenticationService().authenticate(username, password);
    }

    private AuthenticationService getAuthenticationService() {

        try {
            //FIXME: there must be a better way!
            InitialContext ctx = new InitialContext();
            return (AuthenticationService)ctx.lookup("java:global/webapp-1.0-SNAPSHOT/SimpleAuthenticationService");
        } catch (NamingException e) {
            //swallow
        }
        throw new IllegalStateException("cannot perform login, as the bean " + AuthenticationService.class.getName() + " is not configured");
    }
}
