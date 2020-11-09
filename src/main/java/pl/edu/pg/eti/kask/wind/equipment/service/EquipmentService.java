package pl.edu.pg.eti.kask.wind.equipment.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.equipment.repository.EquipmentRepository;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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

    // public List<Equipment> findAllEquipmentsByRental(Long rental) { return repository.findAllEquipmentsByRental(rental); }

    @Transactional
    public void delete(Long equipmentId) {
        Equipment equipment = repository.find(equipmentId).orElseThrow();
        equipment.getUser().getEquipments().remove(equipment);
        repository.delete(equipment);
    }

    @Transactional
    public void create(Equipment equipment) {
        repository.create(equipment);
    }

    @Transactional
    public void deleteByRental(Rental rental) { repository.deleteByRental(rental); }

    // public void deleteAll() { repository.deleteAll(); }

    @Transactional
    public void update(Equipment equipment) { repository.update(equipment);}

    // public void updateByRental(List<Equipment> equipments, Long rentalId) { repository.updateByRental(equipments, rentalId);}
}
