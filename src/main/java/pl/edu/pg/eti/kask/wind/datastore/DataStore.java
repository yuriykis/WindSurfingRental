package pl.edu.pg.eti.kask.wind.datastore;

import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.wind.serialization.CloningUtility;
import pl.edu.pg.eti.kask.wind.user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@Log
@ApplicationScoped
public class DataStore {

    private Set<User> users = new HashSet<>();

    public synchronized Optional<User> findUser(String login) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Optional<User> findUser(String login, String password) throws IllegalArgumentException {
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .filter(users -> users.getPassword().equals(password))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createUser(User user) throws IllegalArgumentException {
        findUser(user.getLogin()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The user login \"%s\" is not unique", user.getLogin()));
                },
                () -> users.add(user));
    }

    public synchronized List<User> findAll() {
        List<User> users = new ArrayList<User>(this.users);
        return users;
    }
}
