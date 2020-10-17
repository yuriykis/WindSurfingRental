package pl.edu.pg.eti.kask.wind.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.wind.user.entity.User;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUserResponse {

    private Integer userId;

    private String login;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String password;

    private String email;

    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .email(user.getEmail())
                .login(user.getLogin())
                .userId(user.getUserId())
                .build();
    }
}
