package pl.edu.pg.eti.kask.wind.rental.view;


import lombok.*;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.rental.model.RentalModel;
import pl.edu.pg.eti.kask.wind.rental.service.RentalService;

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
public class RentalView implements Serializable {

    private final RentalService service;

    @Setter
    @Getter
    private Long id;

    @Getter
    private RentalModel rental;

    @Inject
    public RentalView(RentalService service ) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Rental> rental = service.find(id);
        if (rental.isPresent()) {
            this.rental = RentalModel.entityToModelMapper().apply(rental.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Rental not found");
        }
    }
}
