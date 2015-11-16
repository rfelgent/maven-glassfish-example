package de.rfelgent.edu.service;

import de.rfelgent.edu.persistence.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserService {

    @EJB
    private UserDao userDao;

    public void register(User user) throws UserAlreadyExistsException {
        if (userDao.existsUser(user)) {
            throw new UserAlreadyExistsException();
        }
        userDao.save(user);
    }
}
