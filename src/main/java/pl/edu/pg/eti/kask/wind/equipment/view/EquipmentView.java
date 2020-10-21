package pl.edu.pg.eti.kask.wind.equipment.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.equipment.model.EquipmentModel;
import pl.edu.pg.eti.kask.wind.equipment.service.EquipmentService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequestScoped
@Named
public class EquipmentView {

    private final EquipmentService service;

    @Setter
    @Getter
    private Long id;

    @Getter
    private EquipmentModel equipment;

    @Inject
    public EquipmentView(EquipmentService service ) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Equipment> equipment = service.find(id);
        if (equipment.isPresent()) {
            this.equipment = EquipmentModel.entityToModelMapper().apply(equipment.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Equipment not found");
        }
    }
}
