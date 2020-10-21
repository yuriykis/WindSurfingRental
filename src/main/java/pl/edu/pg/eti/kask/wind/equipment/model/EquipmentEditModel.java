package pl.edu.pg.eti.kask.wind.equipment.model;

import lombok.*;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class EquipmentEditModel {

    private String name;

    private String description;

    private int rentPrice;

    private List<String> componentsNames;

    public static Function<Equipment, EquipmentEditModel> entityToModelMapper() {
        return equipment -> EquipmentEditModel.builder()
                .name(equipment.getName())
                .description(equipment.getDescription())
                .rentPrice(equipment.getRentPrice())
                .componentsNames(equipment.getComponentsNames())
                .build();
    }

    public static BiFunction<Equipment, EquipmentEditModel, Equipment> modelToEntityUpdater() {
        return (equipment, request) -> {
            equipment.setName(request.getName());
            equipment.setDescription(request.getDescription());
            equipment.setRentPrice(request.getRentPrice());
            equipment.setComponentsNames((request.getComponentsNames()));
            return equipment;
        };
    }
}
