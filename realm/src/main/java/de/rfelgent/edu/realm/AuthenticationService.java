package de.rfelgent.edu.realm;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.List;

@Local
public interface AuthenticationService extends Serializable {

    boolean authenticate(String username, String password);

    List<String> getGroups(String username);
}
