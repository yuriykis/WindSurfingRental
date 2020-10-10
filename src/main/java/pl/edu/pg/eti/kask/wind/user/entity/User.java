package pl.edu.pg.eti.kask.wind.user.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;

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
public class User implements Serializable {

    private String login;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String password;

    private String email;

    private List<Equipment> equipment;

    private byte[] avatar;
}
