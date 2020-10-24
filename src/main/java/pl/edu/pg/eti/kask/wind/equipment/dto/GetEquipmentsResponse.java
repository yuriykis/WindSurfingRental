package pl.edu.pg.eti.kask.wind.equipment.dto;

import lombok.*;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.user.entity.User;

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
public class GetEquipmentsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Equipment {
        private Long id;

        private User user;

        private Rental rental;

        private String name;

        private String description;

        private int rentPrice;

        private List<String> componentsNames;
    }


    @Singular
    private List<Equipment> equipments;

    public static Function<Collection<pl.edu.pg.eti.kask.wind.equipment.entity.Equipment>, GetEquipmentsResponse> entityToDtoMapper() {
        return equipments -> {
            GetEquipmentsResponseBuilder response = GetEquipmentsResponse.builder();
            equipments.stream()
                    .map(equipment -> Equipment.builder()
                            .id(equipment.getId())
                            .name(equipment.getName())
                            .description(equipment.getDescription())
                            .rentPrice(equipment.getRentPrice())
                            .componentsNames(equipment.getComponentsNames())
                            .build())
                    .forEach(response::equipment);
            return response.build();
        };
    }
}
