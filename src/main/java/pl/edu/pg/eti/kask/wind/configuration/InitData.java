package pl.edu.pg.eti.kask.wind.configuration;

import pl.edu.pg.eti.kask.wind.user.entity.User;
import pl.edu.pg.eti.kask.wind.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
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
                .login("admin")
                .firstName("Yuriy")
                .lastName("Kis")
                .birthDate(LocalDate.of(1997, 8, 25))
                .email("admin@wind.example.com")
                .password(Sha256Utility.hash("adminadmin"))
                .build();

        User janek = User.builder()
                .login("janek")
                .firstName("Jan")
                .lastName("Kowalski")
                .birthDate(LocalDate.of(1995, 2, 28))
                .email("janek@wind.example.com")
                .password(Sha256Utility.hash("useruser"))
                .build();

        User tomek = User.builder()
                .login("tomek")
                .firstName("Tomasz")
                .lastName("Grabowski")
                .birthDate(LocalDate.of(1996, 6, 4))
                .email("tomek@wind.example.com")
                .password(Sha256Utility.hash("useruser"))
                .build();

        User ania = User.builder()
                .login("ania")
                .firstName("Anna")
                .lastName("Nowak")
                .birthDate(LocalDate.of(1993, 10, 3))
                .email("ania@wind.example.com")
                .password(Sha256Utility.hash("useruser"))
                .build();

        userService.create(admin);
        userService.create(janek);
        userService.create(tomek);
        userService.create(ania);
    }

}
