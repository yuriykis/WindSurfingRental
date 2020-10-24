package pl.edu.pg.eti.kask.wind.equipment.dto;

import lombok.*;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.user.entity.User;

import java.util.List;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateEquipmentRequest {
    private Long id;

    private User user;

    private Rental rental;

    private String name;

    private String description;

    private int rentPrice;

    private List<String> componentsNames;

    public static BiFunction<Equipment, UpdateEquipmentRequest, Equipment> dtoToEntityUpdater() {
        return (equipment, request) -> {
            if(equipment.getName() != null){
                equipment.setName(request.getName());
            }
            if(request.getDescription() != null){
                equipment.setDescription(request.getDescription());
            }
            if(request.getRentPrice() > 0){
                equipment.setRentPrice(request.getRentPrice());
            }
            if(request.getComponentsNames() != null){
                equipment.setComponentsNames(request.getComponentsNames());
            }
            return equipment;
        };
    }
}
