package pl.edu.pg.eti.kask.wind.rental.repository;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.repository.Repository;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class RentalRepository implements Repository<Rental, Long> {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Rental> find(Long id) {
        return Optional.ofNullable(em.find(Rental.class, id));
    }


    @Override
    public List<Rental> findAll() {
        return em.createQuery("select r from Rental r", Rental.class).getResultList();
    }


    @Override
    public void create(Rental entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Rental entity) {
        em.remove(em.find(Rental.class, entity.getId()));
    }

    @Override
    public void detach(Rental entity) {
        em.detach(entity);
    }

    public void deleteAll() {
        em.createQuery("select r from Rental r", Rental.class)
                .getResultList()
                .forEach(this::delete);
    }

    @Override
    public void update(Rental entity) {
        em.merge(entity);
    }

}
