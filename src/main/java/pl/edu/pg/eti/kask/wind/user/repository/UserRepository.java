package pl.edu.pg.eti.kask.wind.user.repository;

import pl.edu.pg.eti.kask.wind.repository.Repository;
import pl.edu.pg.eti.kask.wind.user.entity.User;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import lombok.extern.java.Log;

@RequestScoped
@Log
public class UserRepository implements Repository<User, Integer> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public Optional<User> find(Integer id) {
        log.info(String.format("EntityManager for %s %s find", this.getClass(), em));
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        log.info(String.format("EntityManager for %s %s create", this.getClass(), em));
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        em.remove(em.find(User.class, entity.getLogin()));
    }

    @Override
    public void update(User entity) {
        em.merge(entity);
    }

    @Override
    public void detach(User entity) {
        em.detach(entity);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        try {
            return Optional.of(em.createQuery("select u from User u where u.login = :login and u.password = :password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
