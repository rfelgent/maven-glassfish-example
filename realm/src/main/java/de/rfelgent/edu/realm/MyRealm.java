package de.rfelgent.edu.realm;

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
import java.util.List;
import java.util.Properties;

@Service(name = "myRealm")
public class MyRealm extends BaseRealm {

    public static final String JNDI_AUTH = "jndi-authentication";

    public MyRealm() {
        super();
    }

    @Override
    protected void init(Properties props) throws BadRealmException, NoSuchRealmException {
        _logger.info("\n*************\nStarting own realm " + MyRealm.class.getName() + "\n*************");
        super.init(props);

        if (!props.containsKey(JAAS_CONTEXT_PARAM)) {
            throw new IllegalStateException("jaas-context must be configured by <auth-realm> setting in domain.xml or by asadmin!");
        }
        //TODO: Is there a better way for referencing an EJB via JNDI?
        if (!props.containsKey(JNDI_AUTH)) {
            throw new IllegalStateException("the jndi name of bean " + AuthenticationService.class.getName() + " must be specified");
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
        try {
            InitialContext remoteContext = new InitialContext();
            String jndi = getProperty(JNDI_AUTH);
            if (!jndi.startsWith("java:")) {
                jndi += "java:";
            }
            List<String> groups = ((AuthenticationService) remoteContext.lookup(jndi)).getGroups(username);
            return Collections.enumeration(groups);
        } catch (NamingException e) {
            e.printStackTrace();
            throw new InvalidOperationException("jndi lookup failed");
        }
    }
}
