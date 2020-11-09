package pl.edu.pg.eti.kask.wind.equipment.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@Entity
@Table(name = "equpments")
public class Equipment implements Serializable {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rental")
    private Rental rental;

    private String name;

    private String description;

    private int rentPrice;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<String> componentsNames;

}
