package de.rfelgent.education.realm;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AuthenticationService {

    /**
     * @param username
     * @param password
     * @return a list of roles the user has
     */
    boolean authenticate(String username, String password);

    List<String> getGroups(String username);
}
