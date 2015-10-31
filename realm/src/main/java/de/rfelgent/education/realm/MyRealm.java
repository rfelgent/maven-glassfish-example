package de.rfelgent.education.realm;

import com.sun.enterprise.security.BaseRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import org.jvnet.hk2.annotations.Service;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;

@Service(name = "myRealm")
public class MyRealm extends BaseRealm {

    private Properties properties;

    public MyRealm() {
        super();
    }

    @Override
    protected void init(Properties props) throws BadRealmException, NoSuchRealmException {
        _logger.info("\n*************\nStarting own realm " + MyRealm.class.getName() + "\n*************");
        super.init(props);

        if (!props.containsKey(JAAS_CONTEXT_PARAM)) {
            throw new IllegalStateException("jaas-context must be configured by <auth-realm> setting in domain.xml!");
        }
        for (String propertyName : props.stringPropertyNames()) {
            setProperty(propertyName, props.getProperty(propertyName));
        }
    }

    @Override
    public String getAuthType() {
        return "AuthType";
    }

    @Override
    public Enumeration getGroupNames(String username) throws InvalidOperationException, NoSuchUserException {
        Properties properties = new Properties();
        properties.put("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.put("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        InitialContext remoteContext;
        try {
            remoteContext = new InitialContext(properties);
        } catch (NamingException e) {
            throw new InvalidOperationException("jndi lookup failed");
        }

        AuthenticationService object;
        try {
            object = (AuthenticationService)remoteContext.lookup("java:global/webapp-1.0-SNAPSHOT/AuthenticationService");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new InvalidOperationException("jndi lookup failed");
        }

        return Collections.emptyEnumeration();
    }
}
