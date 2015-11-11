package de.rfelgent.edu.realm;

import de.rfelgent.edu.persistence.User;
import de.rfelgent.edu.persistence.UserRole;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class SimpleAuthenticationService implements AuthenticationService {

    //only and only if there is at least one EJB injecting the PeristenceContext
    //then GF triggers bootstrapping the persistence.xml!
    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean authenticate(String username, String password) {
        //just an demonstration using criteria api (pretty complex, isnt it?)
        CriteriaBuilder cb = em.getCriteriaBuilder();

        ParameterExpression<String> username_exp = cb.parameter(String.class);
        ParameterExpression<String> password_exp = cb.parameter(String.class);

        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> criteria = query.from(User.class);
        query.select(criteria).where(
                cb.equal(criteria.get("username"), username_exp),
                cb.equal(criteria.get("password"), password_exp)
        );

        return em.createQuery(query)
                .setParameter(username_exp, username)
                .setParameter(password_exp, password)
                .getResultList().size() == 1;
    }

    @Override
    public List<String> getGroups(String username) {
        //simple jqpl queries... much less complex compared to criteria api
        return (List<String>)em.createQuery("SELECT a.role FROM UserRole a INNER JOIN a.user b where b.username = :username")
                .setParameter("username", username)
                .getResultList().stream().map(new Function() {
                    @Override
                    public Object apply(Object o) {
                        return ((UserRole.Role)o).name();
                    }
                }).collect(Collectors.<String>toList());
    }

}
