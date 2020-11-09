package pl.edu.pg.eti.kask.wind.equipment.repository;

import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.rental.service.RentalService;
import pl.edu.pg.eti.kask.wind.repository.Repository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class EquipmentRepository implements Repository<Equipment, Long> {

    private EntityManager em;
    private RentalService rentalService;

    @Inject
    public void setRentalService(RentalService rentalService){
        this.rentalService = rentalService;
    }
    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Equipment> find(Long id) {
        return Optional.ofNullable(em.find(Equipment.class, id));
    }

    @Override
    public List<Equipment> findAll() {
        return em.createQuery("select e from Equipment e", Equipment.class).getResultList();
    }

    @Override
    public void create(Equipment entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Equipment entity) {
        em.remove(em.find(Equipment.class, entity.getId()));
    }


    @Override
    public void update(Equipment entity) {
        em.merge(entity);
    }

    @Override
    public void detach(Equipment entity) {
        em.detach(entity);
    }

    // public void updateByRental(List<Equipment> entity, Long rentalId) { store.updateEquipmentsByRental(entity, rentalId); }


    public List<Equipment> findAllByRental(Rental rental) {
        return em.createQuery("select e from Equipment e where e.rental = :rental", Equipment.class)
                .setParameter("rental", rental)
                .getResultList();
    }

    public void deleteByRental(Rental rental) {
            List<Equipment> equipments = findAllByRental(rental);
            equipments.forEach(this::delete);
    }

    // public void deleteAll() { store.deleteAllEquipments(); }
}
