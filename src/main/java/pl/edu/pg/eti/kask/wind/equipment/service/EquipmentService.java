package pl.edu.pg.eti.kask.wind.equipment.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.equipment.repository.EquipmentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class EquipmentService {

    private EquipmentRepository repository;

    @Inject
    public EquipmentService(EquipmentRepository repository) {
        this.repository = repository;
    }

    public List<Equipment> findAll() {
        return repository.findAll();
    }

    public Optional<Equipment> find(Long id) {
        return repository.find(id);
    }

    public List<Equipment> findAllEquipmentsByRental(Long rental) { return repository.findAllEquipmentsByRental(rental); }

    public void delete(Long equipment) {
        repository.delete(repository.find(equipment).orElseThrow());
    }

    public void create(Equipment equipment) {
        repository.create(equipment);
    }

    public void deleteByRental(Long rentalId) { repository.deleteByRental(rentalId); }

    public void deleteAll() { repository.deleteAll(); }

    public void update(Equipment equipment) { repository.update(equipment);}

    public void updateByRental(List<Equipment> equipments, Long rentalId) { repository.updateByRental(equipments, rentalId);}
}
