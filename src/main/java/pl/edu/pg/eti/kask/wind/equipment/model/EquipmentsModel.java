package pl.edu.pg.eti.kask.wind.equipment.model;

import lombok.*;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.rental.model.RentalsModel;
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
public class EquipmentsModel {

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
    private List<EquipmentsModel.Equipment> equipments;

    public static Function<Collection<pl.edu.pg.eti.kask.wind.equipment.entity.Equipment>, EquipmentsModel> entityToModelMapper() {
        return equipments -> {
            EquipmentsModel.EquipmentsModelBuilder model = EquipmentsModel.builder();
            equipments.stream()
                    .map(equipment -> Equipment.builder()
                            .id(equipment.getId())
                            .user(equipment.getUser())
                            .rental(equipment.getRental())
                            .name(equipment.getName())
                            .description(equipment.getDescription())
                            .rentPrice(equipment.getRentPrice())
                            .componentsNames(equipment.getComponentsNames())
                            .build())
                    .forEach(model::equipment);
            return model.build();
        };
    }
}
