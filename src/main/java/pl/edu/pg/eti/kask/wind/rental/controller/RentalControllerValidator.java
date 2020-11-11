package pl.edu.pg.eti.kask.wind.rental.controller;

import pl.edu.pg.eti.kask.wind.rental.dto.CreateRentalRequest;
import pl.edu.pg.eti.kask.wind.rental.dto.UpdateRentalRequest;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RentalControllerValidator {

    public static boolean CreateRentalRequestValid(CreateRentalRequest request) {
        if(request.getName() == null ||
                request.getEstablishDate() == null ||
                request.getCity() == null ||
                request.getNumOfEmployees() == 0){
            return false;
        }
        try {
            LocalDate.parse(request.getEstablishDate());
        }catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static boolean UpdateRentalRequestValid(UpdateRentalRequest request){
        if(request.getEstablishDate() != null) {
            try {
                LocalDate.parse(request.getEstablishDate());
            }catch (DateTimeParseException e){
                return false;
            }
        }
        if(request.getNumOfEmployees() < 0) {
            return false;
        }
        return true;
    }
}
