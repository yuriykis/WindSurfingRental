package pl.edu.pg.eti.kask.wind.rental.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.rental.repository.RentalRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class RentalService {

    private RentalRepository repository;
    @Inject
    public RentalService(RentalRepository repository) {

        this.repository = repository;
    }

    public List<Rental> findAll() {
        return repository.findAll();
    }

    public Optional<Rental> find(Long id) {
        return repository.find(id);
    }

    @Transactional
    public void delete(Long rentalId) {
        Rental rental = repository.find(rentalId).orElseThrow();
        repository.delete(rental);
    }

    @Transactional
    public void deleteAll() { repository.deleteAll(); }

    @Transactional
    public void create(Rental rental) {
        repository.create(rental);
    }

    @Transactional
    public void update(Rental rental) {
        repository.update(rental);
    }

}
