package pl.edu.pg.eti.kask.wind.rental.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Rental implements Serializable {

    private String name;

    private int numOfEmployees;

    private String city;

    private LocalDate establishDate;

    private List<Equipment> equipment;

}
