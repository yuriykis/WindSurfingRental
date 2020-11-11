package pl.edu.pg.eti.kask.wind.equipment.service;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.equipment.repository.EquipmentRepository;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.rental.repository.RentalRepository;
import pl.edu.pg.eti.kask.wind.user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class EquipmentService {

    private EquipmentRepository repository;

    private RentalRepository rentalRepository;

    @Inject
    public EquipmentService(EquipmentRepository repository, RentalRepository rentalRepository) {
        this.repository = repository;
        this.rentalRepository = rentalRepository;
    }

    public List<Equipment> findAll() {
        return repository.findAll();
    }

    public Optional<Equipment> find(Long id) {
        return repository.find(id);
    }

     public List<Equipment> findAllEquipmentsByRental(Rental rental) {
        return repository.findAllEquipmentsByRental(rental);
    }

    @Transactional
    public void delete(Long equipmentId) {
        Equipment equipment = repository.find(equipmentId).orElseThrow();
        User user = equipment.getUser();
        if (user != null) {
            user.getEquipments().remove(equipment);
        }
        Rental rental = equipment.getRental();
        rental.getEquipments().remove(equipment);
        repository.delete(equipment);
    }

    @Transactional
    public void create(Equipment equipment) {
        repository.create(equipment);
        rentalRepository.find(equipment.getRental().getId()).ifPresent(rental -> {
            rental.getEquipments().add(equipment);
        });
    }

    @Transactional
    public void deleteByRental(Rental rental) {
        List<Equipment> equipments = repository.findAllEquipmentsByRental(rental);
        equipments.forEach(equipment -> {
            User user = equipment.getUser();
            if (user != null) {
                user.getEquipments().remove(equipment);
            }
            rental.getEquipments().remove(equipment);
        });
        repository.deleteByRental(rental);
    }

    @Transactional
    public void deleteAll() { repository.deleteAll(); }

    @Transactional
    public void update(Equipment equipment) { repository.update(equipment);}

}
