package de.rfelgent.education.realm;

import de.rfelgent.education.persistence.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Stateless
public class SimpleAuthenticationService implements AuthenticationService {

    //only and only if there is at least one EJB injecting the PeristenceContext
    //then GF triggers bootstrapping the persistence.xml!
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean authenticate(String username, String password) {
        List<User> users = (List<User>) entityManager.createQuery(
                "from User user where user.username = '" + username + "' AND user.password = '" + password + "'").getResultList();
        return users.size() == 1;
    }

    @Override
    public List<String> getGroups(String username) {
        return new ArrayList<>(Arrays.asList("user"));
    }


}
