package de.rfelgent.edu.service;

import de.rfelgent.edu.persistence.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    public boolean existsUser(User user) {
        return !em.createQuery(
                "SELECT a from User a where a.username = :username")
                .setParameter("username", user.getUsername())
                .setMaxResults(1)
                .getResultList().isEmpty();
    }

    public void save(User user) {
        em.persist(user);
    }
}
