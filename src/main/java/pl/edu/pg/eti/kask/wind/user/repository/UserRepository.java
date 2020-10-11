package pl.edu.pg.eti.kask.wind.user.repository;

import pl.edu.pg.eti.kask.wind.datastore.DataStore;
import pl.edu.pg.eti.kask.wind.repository.Repository;
import pl.edu.pg.eti.kask.wind.user.entity.User;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class UserRepository implements Repository<User, Integer> {
    private DataStore store;

    @Inject
    public UserRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<User> find(Integer id) {
        return store.findUser(id);
    }

    @Override
    public List<User> findAll() {
        return store.findAll();
    }

    @Override
    public void create(User entity) {
        store.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(User entity) {
        store.updateUser(entity);
    }


    public Optional<User> findByLoginAndPassword(String login, String password) {
        return store.findUser(login, password);
    }
}
