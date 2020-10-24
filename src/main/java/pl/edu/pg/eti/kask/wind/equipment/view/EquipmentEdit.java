package pl.edu.pg.eti.kask.wind.equipment.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.equipment.model.EquipmentEditModel;
import pl.edu.pg.eti.kask.wind.equipment.service.EquipmentService;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@ViewScoped
@Named
public class EquipmentEdit implements Serializable {

    private final EquipmentService service;


    @Setter
    @Getter
    private Long id;

    @Getter
    private EquipmentEditModel equipment;


    @Inject
    public EquipmentEdit(EquipmentService service) {
        this.service = service;
    }



    public void init() throws IOException {
        Optional<Equipment> equipment = service.find(id);
        if (equipment.isPresent()) {
            this.equipment = EquipmentEditModel.entityToModelMapper().apply(equipment.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Equipment not found");
        }
    }


    public String saveAction() {
        service.update(EquipmentEditModel.modelToEntityUpdater().apply(service.find(id).orElseThrow(), equipment));
        return "equipment_view?id=" + this.id + "&faces-redirect=true";
    }

}
