package pl.edu.pg.eti.kask.wind.configuration;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.equipment.service.EquipmentService;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.rental.service.RentalService;
import pl.edu.pg.eti.kask.wind.user.entity.User;
import pl.edu.pg.eti.kask.wind.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;

import pl.edu.pg.eti.kask.wind.security.Sha256Utility;

@ApplicationScoped
public class InitData {
    private final UserService userService;
    private final RentalService rentalService;
    private final EquipmentService equipmentService;

    @Inject
    public InitData(UserService userService, RentalService rentalService, EquipmentService equipmentService) {

        this.userService = userService;
        this.rentalService = rentalService;
        this.equipmentService = equipmentService;
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

        Rental mainRentalOffice = Rental.builder()
                .id(Long.valueOf(1))
                .name("MainOffice")
                .numOfEmployees(20)
                .city("Gdansk")
                .establishDate(LocalDate.parse("2018-11-01"))
                .build();

        Rental firstRentalOffice = Rental.builder()
                .id(Long.valueOf(2))
                .name("FirstOffice")
                .numOfEmployees(8)
                .city("Gdynia")
                .establishDate(LocalDate.parse("2018-11-01"))
                .build();

        Rental secondRentalOffice = Rental.builder()
                .id(Long.valueOf(3))
                .name("SecondOffice")
                .numOfEmployees(12)
                .city("Sopot")
                .establishDate(LocalDate.parse("2018-11-01"))
                .build();


        rentalService.create(mainRentalOffice);
        rentalService.create(firstRentalOffice);
        rentalService.create(secondRentalOffice);



        Equipment proEquipment1 = Equipment.builder()
                .id(Long.valueOf(1))
                .user(admin)
                .rental(mainRentalOffice)
                .name("proEquipment1")
                .description("Windsurfing equipment for professionals")
                .rentPrice(670)
                .componentsNames(Arrays.asList("Board1", "Sail1", "Wetsuit1", "Boom1"))
                .build();

        Equipment proEquipment2 = Equipment.builder()
                .id(Long.valueOf(2))
                .rental(mainRentalOffice)
                .name("proEquipment2")
                .description("Windsurfing equipment for professionals")
                .rentPrice(670)
                .componentsNames(Arrays.asList("Board2", "Sail2", "Wetsuit2", "Boom2"))
                .build();

        Equipment beginnerEquipment1 = Equipment.builder()
                .id(Long.valueOf(3))
                .rental(mainRentalOffice)
                .name("beginnerEquipment1")
                .description("Windsurfing equipment for beginners")
                .rentPrice(300)
                .componentsNames(Arrays.asList("Board3", "Sail3", "Wetsuit3", "Boom3"))
                .build();

        Equipment middleEquipment = Equipment.builder()
                .id(Long.valueOf(4))
                .user(janek)
                .rental(firstRentalOffice)
                .name("middleEquipment")
                .description("Windsurfing equipment for middles")
                .rentPrice(410)
                .componentsNames(Arrays.asList("Board4", "Sail4", "Wetsuit4", "Boom4"))
                .build();

        Equipment proEquipment3 = Equipment.builder()
                .id(Long.valueOf(5))
                .rental(firstRentalOffice)
                .name("proEquipment3")
                .description("Windsurfing equipment for professionals")
                .rentPrice(670)
                .componentsNames(Arrays.asList("Board4", "Sail4", "Wetsuit4", "Boom4"))
                .build();

        Equipment beginnerEquipment2 = Equipment.builder()
                .id(Long.valueOf(6))
                .user(janek)
                .rental(secondRentalOffice)
                .name("beginnerEquipment2")
                .description("Windsurfing equipment for beginners")
                .rentPrice(300)
                .componentsNames(Arrays.asList("Board5", "Sail5", "Wetsuit5", "Boom5"))
                .build();

        equipmentService.create(proEquipment1);
        equipmentService.create(proEquipment2);
        equipmentService.create(middleEquipment);
        equipmentService.create(beginnerEquipment1);
        equipmentService.create(proEquipment3);
        equipmentService.create(beginnerEquipment2);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        Path filePath = Paths.get(AvatarsFolder.AVATARS_FOLDER, name);
        return Files.readAllBytes(filePath);
    }
}
