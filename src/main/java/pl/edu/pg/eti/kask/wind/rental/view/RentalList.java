package pl.edu.pg.eti.kask.wind.rental.view;

import pl.edu.pg.eti.kask.wind.equipment.service.EquipmentService;
import pl.edu.pg.eti.kask.wind.rental.model.RentalsModel;
import pl.edu.pg.eti.kask.wind.rental.service.RentalService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class RentalList implements Serializable {

    private final RentalService service;
    private final EquipmentService equipmentService;

    private RentalsModel rentals;

    @Inject
    public RentalList(RentalService service, EquipmentService equipmentService) {

        this.service = service;
        this.equipmentService = equipmentService;
    }

    public RentalsModel getRentals() {
        if (rentals == null) {
            rentals = RentalsModel.entityToModelMapper().apply(service.findAll());
        }
        return rentals;
    }

    public String deleteAction(RentalsModel.Rental rental) {
        service.delete(rental.getId());
        equipmentService.deleteByRental(rental.getId());
        return "rental_list?faces-redirect=true";
    }
}
