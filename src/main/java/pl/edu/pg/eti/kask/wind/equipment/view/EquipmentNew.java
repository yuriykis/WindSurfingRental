package pl.edu.pg.eti.kask.wind.equipment.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.equipment.model.EquipmentNewModel;
import pl.edu.pg.eti.kask.wind.equipment.service.EquipmentService;
import pl.edu.pg.eti.kask.wind.rental.service.RentalService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class EquipmentNew implements Serializable {

    private static final int INDEX_DB = 1;
    private final EquipmentService service;
    private final RentalService rentalService;


    @Getter
    private EquipmentNewModel equipment;

    @Getter
    @Setter
    private Long rentalId;

    @Getter
    private Long id;

    @Inject
    public EquipmentNew(EquipmentService service, RentalService rentalService) {
        this.service = service;
        this.rentalService = rentalService;
    }

    public void init(){
        List<Equipment> equipments = service.findAll();
        if (!equipments.isEmpty()){
            this.id = Collections.max(equipments
                    .stream()
                    .map(Equipment::getId)
                    .collect(Collectors.toList())) + INDEX_DB;
        }else {
            this.id = 1L;
        }

        this.equipment = EquipmentNewModel.builder().build();
        this.equipment.setRental(rentalService.find(this.rentalId).get());
        this.equipment.setId(id);
    }

    public String saveAction() {
        service.create(EquipmentNewModel.entityToModelCreator().apply(equipment));
        return "/rental/rental_view?id=" + this.rentalId + "&faces-redirect=true";
    }

}
