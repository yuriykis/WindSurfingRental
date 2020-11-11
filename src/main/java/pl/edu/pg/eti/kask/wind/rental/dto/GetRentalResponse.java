package pl.edu.pg.eti.kask.wind.rental.dto;

import lombok.*;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetRentalResponse {

    private Long id;

    private String name;

    private int numOfEmployees;

    private String city;

    private String establishDate;


    public static Function<Rental, GetRentalResponse> entityToDtoMapper() {
        return rental -> GetRentalResponse.builder()
                .id(rental.getId())
                .name(rental.getName())
                .city(rental.getCity())
                .establishDate(rental.getEstablishDate().toString())
                .numOfEmployees(rental.getNumOfEmployees())
                .build();
    }
}
