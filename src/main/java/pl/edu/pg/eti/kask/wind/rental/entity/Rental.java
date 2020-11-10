package pl.edu.pg.eti.kask.wind.rental.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "rentals")
public class Rental implements Serializable {

    @Id
    private Long id;

    private String name;

    private int numOfEmployees;

    private String city;

    @Column(name = "establish_date")
    private LocalDate establishDate;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "rental", cascade = CascadeType.REMOVE)
    private List<Equipment> equipments;

}
