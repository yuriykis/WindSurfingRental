package pl.edu.pg.eti.kask.wind.user.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.wind.user.entity.User;
import pl.edu.pg.eti.kask.wind.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
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

    public Optional<User> find(Integer id) {
        return repository.find(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void setAvatar(Integer id, InputStream is) {
        repository.find(id).ifPresent(user -> {
            try {
                user.setAvatar(is.readAllBytes());
                repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public void updateAvatar(Integer id, InputStream is) {
        repository.find(id).ifPresent(user -> {
            try {
                user.setAvatar(is.readAllBytes());
                repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public void deleteAvatar(Integer id) {
        repository.find(id).ifPresent(user -> {

            user.setAvatar(null);
            repository.update(user);

        });
    }
}
