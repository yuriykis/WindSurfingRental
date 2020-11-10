package pl.edu.pg.eti.kask.wind.rental.model;

import lombok.*;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RentalModel {

    private Long id;

    private String name;

    private int numOfEmployees;

    private String city;

    private LocalDate establishDate;

    private List<Equipment> equipments;

    public static Function<Rental, RentalModel> entityToModelMapper() {
        return rental -> RentalModel.builder()
                .id(rental.getId())
                .name(rental.getName())
                .numOfEmployees(rental.getNumOfEmployees())
                .city(rental.getCity())
                .establishDate(rental.getEstablishDate())
                .equipments(rental.getEquipments())
                .build();
    }
}
