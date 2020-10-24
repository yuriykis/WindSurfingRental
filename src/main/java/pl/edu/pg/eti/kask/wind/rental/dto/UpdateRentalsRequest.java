package pl.edu.pg.eti.kask.wind.rental.dto;

import lombok.*;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;

import java.util.List;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateRentalsRequest {

    private List<Rental> rentals;

    public static BiFunction<List<Rental>, UpdateRentalsRequest, List<Rental>> listDtoToListEntityUpdater() {
        return (rentals, request) -> {
            rentals.clear();
            rentals.addAll(request.getRentals());
            return rentals;
        };
    }
}
