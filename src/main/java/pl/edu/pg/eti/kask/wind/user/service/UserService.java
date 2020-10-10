package pl.edu.pg.eti.kask.wind.user.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.wind.user.entity.User;
import pl.edu.pg.eti.kask.wind.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class UserService {

    private UserRepository repository;

    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void create(User user) {
        repository.create(user);
    }

    public Optional<User> find(String login) {
        return repository.find(login);
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
