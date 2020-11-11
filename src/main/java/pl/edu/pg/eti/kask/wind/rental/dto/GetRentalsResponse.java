package pl.edu.pg.eti.kask.wind.rental.dto;


import lombok.*;

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
public class GetRentalsResponse {

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

        private String establishDate;

    }

    @Singular
    private List<Rental> rentals;

    public static Function<Collection<pl.edu.pg.eti.kask.wind.rental.entity.Rental>, GetRentalsResponse> entityToDtoMapper() {
        return rentals -> {
            GetRentalsResponseBuilder response = GetRentalsResponse.builder();
            rentals.stream()
                    .map(rental -> Rental.builder()
                            .id(rental.getId())
                            .name(rental.getName())
                            .numOfEmployees(rental.getNumOfEmployees())
                            .city(rental.getCity())
                            .establishDate(rental.getEstablishDate().toString())
                            .build())
                    .forEach(response::rental);
            return response.build();
        };
    }

}
