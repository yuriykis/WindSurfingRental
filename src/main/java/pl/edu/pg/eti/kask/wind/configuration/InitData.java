package pl.edu.pg.eti.kask.wind.configuration;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.wind.user.entity.User;
import pl.edu.pg.eti.kask.wind.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import pl.edu.pg.eti.kask.wind.security.Sha256Utility;

@ApplicationScoped
public class InitData {
    private final UserService userService;

    @Inject
    public InitData(UserService userService) {
        this.userService = userService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        User admin = User.builder()
                .userId(1)
                .login("admin")
                .firstName("Yuriy")
                .lastName("Kis")
                .birthDate(LocalDate.of(1997, 8, 25))
                .email("admin@wind.example.com")
                .password(Sha256Utility.hash("adminadmin"))
                .avatar(getResourceAsByteArray("admin.png"))
                .build();

        User janek = User.builder()
                .userId(2)
                .login("janek")
                .firstName("Jan")
                .lastName("Kowalski")
                .birthDate(LocalDate.of(1995, 2, 28))
                .email("janek@wind.example.com")
                .password(Sha256Utility.hash("useruser"))
                .avatar(getResourceAsByteArray("janek.png"))
                .build();

        User tomek = User.builder()
                .userId(3)
                .login("tomek")
                .firstName("Tomasz")
                .lastName("Grabowski")
                .birthDate(LocalDate.of(1996, 6, 4))
                .email("tomek@wind.example.com")
                .password(Sha256Utility.hash("useruser"))
                .avatar(getResourceAsByteArray("tomek.png"))
                .build();

        User ania = User.builder()
                .userId(4)
                .login("ania")
                .firstName("Anna")
                .lastName("Nowak")
                .birthDate(LocalDate.of(1993, 10, 3))
                .email("ania@wind.example.com")
                .password(Sha256Utility.hash("useruser"))
                .avatar(getResourceAsByteArray("ania.png"))
                .build();

        userService.create(admin);
        userService.create(janek);
        userService.create(tomek);
        userService.create(ania);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        //try (InputStream is = this.getClass().getResourceAsStream(name)) {
        //    return is.readAllBytes();
        //}
        Path filePath = Paths.get(AvatarsFolder.AVATARS_FOLDER, name);
        return Files.readAllBytes(filePath);
    }
}
