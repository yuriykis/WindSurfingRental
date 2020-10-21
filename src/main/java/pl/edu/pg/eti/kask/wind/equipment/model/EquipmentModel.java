package pl.edu.pg.eti.kask.wind.equipment.model;

import lombok.*;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.user.entity.User;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class EquipmentModel {

    private Long id;

    private User user;

    private Rental rental;

    private String name;

    private String description;

    private int rentPrice;

    private List<String> componentsNames;

    public static Function<Equipment, EquipmentModel> entityToModelMapper() {
        return equipment -> EquipmentModel.builder()
                .id(equipment.getId())
                .user(equipment.getUser())
                .rental(equipment.getRental())
                .name(equipment.getName())
                .description(equipment.getDescription())
                .rentPrice(equipment.getRentPrice())
                .componentsNames(equipment.getComponentsNames())
                .build();
    }
}
