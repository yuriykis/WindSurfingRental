package pl.edu.pg.eti.kask.wind.rental.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.rental.repository.RentalRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

    public void delete(Long rental) {
        repository.delete(repository.find(rental).orElseThrow());
    }

    public void deleteAll() { repository.deleteAll(); }

    public void create(Rental rental) {
        repository.create(rental);
    }

    public void update(Rental rental) {
        repository.update(rental);
    }

    public void updateAll(List<Rental> rentals) { repository.updateAll(rentals); }
}
