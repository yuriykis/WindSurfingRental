package pl.edu.pg.eti.kask.wind.equipment.controller;

import pl.edu.pg.eti.kask.wind.equipment.dto.*;
import pl.edu.pg.eti.kask.wind.equipment.entity.Equipment;
import pl.edu.pg.eti.kask.wind.equipment.service.EquipmentService;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.rental.service.RentalService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/rentals/{rentalId}/equipments")
public class RentalEquipmentController {

    private static final int INDEX_DB = 1;
    private static final Long NEW_DB_RECORD_INDEX = 1L;

    RentalService rentalService;

    EquipmentService equipmentService;

    public RentalEquipmentController () {
    }

    @Inject
    public void setRentalService(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Inject
    public void setEquipmentService(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEquipments(@PathParam("rentalId") Long rentalId) {
        Optional<Rental> rental = rentalService.find(rentalId);
        if (rental.isPresent()) {
            List<Equipment> equipments = equipmentService.findAllEquipmentsByRental(rental.get());
            if (equipments.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok(GetEquipmentsResponse.entityToDtoMapper().apply(equipments)).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{equipmentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEquipment(@PathParam("rentalId") Long rentalId, @PathParam("equipmentId") Long equipmentId) {
        Optional<Rental> rental = rentalService.find(rentalId);
        Optional<Equipment> equipment = equipmentService.find(equipmentId);
        if(rental.isPresent() && equipment.isPresent()){
            if(equipment.get().getRental().equals(rental.get())){
                return Response.ok(GetEquipmentResponse.entityToDtoMapper()
                        .apply(equipment.get()))
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEquipment(@PathParam("rentalId") Long rentalId, CreateEquipmentRequest request){
        Optional<Rental> rental = rentalService.find(rentalId);
        if(rental.isPresent()){
            if(request.getName() == null ||
                    request.getDescription() == null ||
                    request.getRentPrice() < 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            List<Equipment> equipments = equipmentService.findAll();
            long newEquipmentId;
            if (!equipments.isEmpty()){
                newEquipmentId = Collections.max(equipments
                        .stream()
                        .map(Equipment::getId)
                        .collect(Collectors.toList())) + INDEX_DB;
            }else {
                newEquipmentId = NEW_DB_RECORD_INDEX;
            }
            Equipment equipment = CreateEquipmentRequest.dtoToEntityMapper().apply(request);
            equipment.setId(newEquipmentId);
            equipment.setRental(rental.get());
            equipmentService.create(equipment);
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{equipmentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEquipment(@PathParam("rentalId") Long rentalId, @PathParam("equipmentId") Long equipmentId, UpdateEquipmentRequest request){
        if(request.getRentPrice() < 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<Rental> rental = rentalService.find(rentalId);
        Optional<Equipment> equipment = equipmentService.find(equipmentId);
        if(rental.isPresent() && equipment.isPresent()){

            if(equipment.get().getRental().equals(rental.get())){

                UpdateEquipmentRequest.dtoToEntityUpdater().apply(equipment.get(), request);
                equipmentService.update(equipment.get());

                return Response.status(Response.Status.ACCEPTED).build();

            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    public Response deleteEquipments(@PathParam("rentalId") Long rentalId){
        Optional<Rental> rental = rentalService.find(rentalId);
        if(rental.isPresent()){
            equipmentService.deleteByRental(rental.get());
            return Response.status(Response.Status.OK).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{equipmentId}")
    public Response deleteEquipment(@PathParam("rentalId") Long rentalId, @PathParam("equipmentId") Long equipmentId){
        Optional<Rental> rental = rentalService.find(rentalId);
        Optional<Equipment> equipment = equipmentService.find(equipmentId);
        if(rental.isPresent() && equipment.isPresent()) {

            if(equipment.get().getRental().equals(rental.get())){

                equipmentService.delete(equipmentId);
                return Response.status(Response.Status.OK).build();

            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
