package pl.edu.pg.eti.kask.wind.equipment.dto;

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
public class CreateEquipmentRequest {
    private Long id;

    private User user;

    private Rental rental;

    private String name;

    private String description;

    private int rentPrice;

    private List<String> componentsNames;

    public static Function<CreateEquipmentRequest, Equipment> dtoToEntityMapper() {
        return request -> Equipment.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .rentPrice(request.getRentPrice())
                .componentsNames(request.getComponentsNames())
                .build();
    }
}
