package pl.edu.pg.eti.kask.wind.rental.repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import pl.edu.pg.eti.kask.wind.datastore.DataStore;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependent
public class RentalRepository implements Repository<Rental, Long> {

    private DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public RentalRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Rental> find(Long id) {
        return store.findRental(id);
    }

    @Override
    public List<Rental> findAll() {
        return store.findAllRentals();
    }

    @Override
    public void create(Rental entity) {
        store.createRental(entity);
    }

    @Override
    public void delete(Rental entity) {
        store.deleteRental(entity.getId());
    }

    @Override
    public void update(Rental entity) { store.updateRental(entity); }

}
