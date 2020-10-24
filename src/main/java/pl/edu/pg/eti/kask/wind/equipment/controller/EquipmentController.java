package pl.edu.pg.eti.kask.wind.equipment.controller;

import pl.edu.pg.eti.kask.wind.equipment.dto.GetEquipmentResponse;
import pl.edu.pg.eti.kask.wind.equipment.dto.GetEquipmentsResponse;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.equipment.service.EquipmentService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/equipments")
public class EquipmentController {

    EquipmentService equipmentService;

    @Inject
    public void setEquipmentService(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEquipments() {
        List<Equipment> equipments = equipmentService.findAll();
        if (equipments.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(GetEquipmentsResponse.entityToDtoMapper().apply(equipments)).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEquipment(@PathParam("id") Long id){
        Optional<Equipment> equipment = equipmentService.find(id);
        if(equipment.isPresent()){
            return Response.ok(GetEquipmentResponse.entityToDtoMapper().apply(equipment.get())).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
