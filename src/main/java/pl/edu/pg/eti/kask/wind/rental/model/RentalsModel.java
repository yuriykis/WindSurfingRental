package pl.edu.pg.eti.kask.wind.rental.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RentalsModel implements Serializable {


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Rental {

        private Long id;

        private String name;

        private int numOfEmployees;

        private String city;

        private LocalDate establishDate;

    }

    @Singular
    private List<Rental> rentals;

    public static Function<Collection<pl.edu.pg.eti.kask.wind.rental.entity.Rental>, RentalsModel> entityToModelMapper() {
        return rentals -> {
            RentalsModel.RentalsModelBuilder model = RentalsModel.builder();
            rentals.stream()
                    .map(rental -> RentalsModel.Rental.builder()
                            .id(rental.getId())
                            .name(rental.getName())
                            .numOfEmployees(rental.getNumOfEmployees())
                            .city(rental.getCity())
                            .establishDate(rental.getEstablishDate())
                            .build())
                    .forEach(model::rental);
            return model.build();
        };
    }

}
