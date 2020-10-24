package pl.edu.pg.eti.kask.wind.equipment.repository;

import pl.edu.pg.eti.kask.wind.datastore.DataStore;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class EquipmentRepository implements Repository<Equipment, Long> {

    private DataStore store;

    @Inject
    public EquipmentRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Equipment> find(Long id) {
        return store.findEquipment(id);
    }

    @Override
    public List<Equipment> findAll() {
        return store.findAllEquipments();
    }

    @Override
    public void create(Equipment entity) {
        store.createEquipment(entity);
    }

    @Override
    public void delete(Equipment entity) {
        store.deleteEquipment(entity.getId());
    }

    @Override
    public void update(Equipment entity) { store.updateEquipment(entity); }

    public void updateByRental(List<Equipment> entity, Long rentalId) { store.updateEquipmentsByRental(entity, rentalId); }

    public List<Equipment> findAllEquipmentsByRental (Long rental) {
        return store.findAllEquipmentsByRental(rental);
    }

    public void deleteByRental(Long rentalId) { store.deleteEquipmentByRental(rentalId); }

    public void deleteAll() { store.deleteAllEquipments(); }
}
