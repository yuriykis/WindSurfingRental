package pl.edu.pg.eti.kask.wind.equipment.controller;

import pl.edu.pg.eti.kask.wind.equipment.dto.CreateEquipmentRequest;
import pl.edu.pg.eti.kask.wind.equipment.dto.UpdateEquipmentRequest;

public class RentalEquipmentControllerValidator {

    public static boolean CreateEquipmentRequestValid(CreateEquipmentRequest request) {
        if(request.getName() == null ||
                request.getDescription() == null ||
                request.getRentPrice() < 0) {
            return false;
        }
        return true;
    }

    public static boolean UpdateEquipmentRequestValid(UpdateEquipmentRequest request){
        if(request.getRentPrice() < 0) {
            return false;
        }
        return true;
    }
}
