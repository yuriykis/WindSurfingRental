package pl.edu.pg.eti.kask.wind.datastore;

import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.repository.Repository;
import pl.edu.pg.eti.kask.wind.serialization.CloningUtility;
import pl.edu.pg.eti.kask.wind.user.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
public class DataStore {

    private Set<User> users = new HashSet<>();
    private Set<Rental> rentals = new HashSet<>();
    private Set<Equipment> equipments = new HashSet<>();


    // Users

    public synchronized Optional<User> findUser(Integer id) {
        return users.stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized Optional<User> findUser(String login, String password) throws IllegalArgumentException {
        return users.stream()
                .filter(user -> user.getLogin().equals(login))
                .filter(users -> users.getPassword().equals(password))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createUser(User user) throws IllegalArgumentException {
        findUser(user.getUserId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The user login \"%s\" is not unique", user.getLogin()));
                },
                () -> users.add(user));
    }

    public synchronized void updateUser(User user) throws IllegalArgumentException {
        findUser(user.getUserId()).ifPresentOrElse(
                original -> {
                    users.remove(original);
                    users.add(user);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The user with id \"%d\" does not exist", user.getUserId()));
                });
    }

    public synchronized List<User> findAll() {
        List<User> users = new ArrayList<User>(this.users);
        return users;
    }


    // Rentals

    public synchronized List<Rental> findAllRentals() {
        return rentals.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public synchronized Optional<Rental> findRental(Long id) {
        return rentals.stream()
                .filter(character -> character.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createRental(Rental rental) throws IllegalArgumentException {
        rental.setId(findAllRentals().stream().mapToLong(Rental::getId).max().orElse(0) + 1);
        rentals.add(rental);
    }

    public synchronized void deleteRental(Long id) throws IllegalArgumentException {
        findRental(id).ifPresentOrElse(
                original -> rentals.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The Rental with id \"%d\" does not exist", id));
                });
    }



    //Equipment

    public synchronized Optional<Equipment> findEquipment(Long id) {
        return equipments.stream()
                .filter(equipments -> equipments.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<Equipment> findAllEquipments() {
        return equipments.stream().map(CloningUtility::clone).collect(Collectors.toList());
    }

    public synchronized void createEquipment(Equipment equipment) throws IllegalArgumentException {
        equipment.setId(findAllEquipments().stream().mapToLong(Equipment::getId).max().orElse(0) + 1);
        equipments.add(equipment);
    }

    public synchronized void deleteEquipment(Long id) throws IllegalArgumentException {
        findEquipment(id).ifPresentOrElse(
                original -> equipments.remove(original),
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The equipment with id \"%d\" does not exist", id));
                });
    }

    public synchronized List<Equipment> findAllEquipmentsByRental (Long rentalId) {
        return equipments
                .stream()
                .filter(equipment -> equipment.getRental().getId().equals(rentalId))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void deleteEquipmentByRental (Long rentalId) {
        List<Equipment> equipmentsToRemove = equipments
                .stream()
                .filter(equipment -> equipment.getRental().getId().equals(rentalId))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
         equipmentsToRemove.forEach(equipment -> deleteEquipment(equipment.getId()));

    }

    public synchronized void updateEquipment(Equipment equipment) throws IllegalArgumentException {
        findEquipment(equipment.getId()).ifPresentOrElse(
                original -> {
                    equipments.remove(original);
                    equipments.add(equipment);
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The equipment with id \"%d\" does not exist", equipment.getId()));
                });
    }
}
