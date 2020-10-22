package pl.edu.pg.eti.kask.wind.rental.dto;

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
public class CreateRentalRequest {

    private Long id;

    private String name;

    private int numOfEmployees;

    private String city;

    private String establishDate;

    private List<Equipment> equipment;

    public static Function<CreateRentalRequest, Rental> dtoToEntityMapper() {
        return request -> Rental.builder()
                .id(request.getId())
                .name(request.getName())
                .city(request.getCity())
                .establishDate(LocalDate.parse(request.getEstablishDate()))
                .numOfEmployees(request.getNumOfEmployees())
                .equipment(request.getEquipment())
                .build();
    }
}
