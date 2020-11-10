package pl.edu.pg.eti.kask.wind.equipment.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.wind.equipment.model.EquipmentsModel;
import pl.edu.pg.eti.kask.wind.equipment.service.EquipmentService;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.rental.service.RentalService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;

@ViewScoped
@Named
public class EquipmentList implements Serializable  {

    @Setter
    @Getter
    private Long rentalId;

    private final EquipmentService service;

    private final RentalService rentalService;

    private EquipmentsModel equipments;

    @Inject
    public EquipmentList(EquipmentService service, RentalService rentalService) {
        this.service = service;
        this.rentalService = rentalService;
    }

    public EquipmentsModel getEquipments() {
        if (equipments == null) {
            Optional<Rental> rental = rentalService.find(this.rentalId);
            rental.ifPresent(value ->
                    equipments = EquipmentsModel.entityToModelMapper()
                            .apply(service.findAllEquipmentsByRental(value)));
        }
        return equipments;
    }

    public String deleteAction(EquipmentsModel.Equipment equipment) {
        service.delete(equipment.getId());
        return "rental_view?id=" + this.rentalId + "&faces-redirect=true";
    }
}
