package pl.edu.pg.eti.kask.wind.equipment.dto;

import lombok.*;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;

import java.util.List;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateEquipmentsRequest {

    List<Equipment> equipments;

    public static BiFunction<List<Equipment>, UpdateEquipmentsRequest, List<Equipment>> listDtoToListEntityUpdater() {
        return (equipments, request) -> {
            equipments.clear();
            equipments.addAll(request.getEquipments());
            return equipments;
        };
    }
}
