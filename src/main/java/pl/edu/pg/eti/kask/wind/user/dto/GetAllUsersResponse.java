package pl.edu.pg.eti.kask.wind.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.wind.user.entity.User;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Builder
@EqualsAndHashCode
public class GetAllUsersResponse {

    private List<GetUserResponse> users;

    public static Function<List<User>, GetAllUsersResponse> entityToDtoMapper() {
        return users -> GetAllUsersResponse.builder()
                .users(users.stream()
                        .map(user -> GetUserResponse.entityToDtoMapper().apply(user))
                        .collect(Collectors.toList()))
                        .build();
    }
}
