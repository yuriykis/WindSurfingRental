package pl.edu.pg.eti.kask.wind.equipment.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.wind.equipment.model.EquipmentsModel;
import pl.edu.pg.eti.kask.wind.equipment.service.EquipmentService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class EquipmentList implements Serializable  {

    @Setter
    @Getter
    private Long rentalId;

    private final EquipmentService service;

    private EquipmentsModel equipments;

    @Inject
    public EquipmentList(EquipmentService service) {
        this.service = service;
    }

    public EquipmentsModel getEquipments() {
        if (equipments == null) {
            equipments = EquipmentsModel.entityToModelMapper().apply(service.findAllEquipmentsByRental(this.rentalId));
        }
        return equipments;
    }

    public String deleteAction(EquipmentsModel.Equipment equipment) {
        service.delete(equipment.getId());
        return "rental_view?id=" + this.rentalId + "&faces-redirect=true";
    }
}
