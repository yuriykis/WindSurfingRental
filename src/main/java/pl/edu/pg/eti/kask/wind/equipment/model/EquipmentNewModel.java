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
public class EquipmentNewModel {


    private Long id;

    private User user;

    private Rental rental;

    private String name;

    private String description;

    private int rentPrice;

    private List<String> componentsNames;

    public static Function<Equipment, EquipmentNewModel> entityToModelMapper() {
        return equipment -> EquipmentNewModel.builder()
                .name(equipment.getName())
                .description(equipment.getDescription())
                .rentPrice(equipment.getRentPrice())
                .componentsNames(equipment.getComponentsNames())
                .build();
    }

    public static Function<EquipmentNewModel, Equipment> entityToModelCreator() {
        return model -> Equipment.builder()
                .id(model.getId())
                .user(model.getUser())
                .rental(model.getRental())
                .name(model.getName())
                .description(model.getDescription())
                .rentPrice(model.getRentPrice())
                .componentsNames(model.getComponentsNames())
                .build();
    }
}
