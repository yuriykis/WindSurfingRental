package pl.edu.pg.eti.kask.wind.rental.dto;

import lombok.*;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateRentalRequest {
    private Long id;

    private String name;

    private int numOfEmployees;

    private String city;

    private String establishDate;

    private List<Equipment> equipment;

    public static BiFunction<Rental, UpdateRentalRequest, Rental> dtoToEntityUpdater() {
        return (rental, request) -> {
            if(request.getName() != null){
                rental.setName(request.getName());
            }
            if(request.getCity() != null){
                rental.setCity(request.getCity());
            }
            if(request.getEstablishDate() != null){
                rental.setEstablishDate(LocalDate.parse(request.getEstablishDate()));
            }
            if(request.getNumOfEmployees() >= 0){
                rental.setNumOfEmployees(request.getNumOfEmployees());
            }
            if(request.getEquipment() != null){
                rental.setEquipment(request.getEquipment());
            }
            return rental;
        };
    }
}
