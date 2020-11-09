package pl.edu.pg.eti.kask.wind.rental.controller;

import pl.edu.pg.eti.kask.wind.equipment.service.EquipmentService;
import pl.edu.pg.eti.kask.wind.rental.dto.*;
import pl.edu.pg.eti.kask.wind.rental.entity.Rental;
import pl.edu.pg.eti.kask.wind.rental.service.RentalService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/rentals")
public class RentalController {

    private RentalService service;
    private EquipmentService equipmentService;
    private static final int INDEX_DB = 1;
    private static final Long NEW_DB_RECORD_INDEX = 1L;

    public RentalController() {
    }

    @Inject
    public void setServices(RentalService service, EquipmentService equipmentService){

        this.service = service;
        this.equipmentService = equipmentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRentals() {
        return Response.ok(GetRentalsResponse.entityToDtoMapper()
                .apply(service.findAll()))
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRental(@PathParam("id") Long id) {
        Optional<Rental> rental = service.find(id);
        if (rental.isPresent()) {
            return Response.ok(GetRentalResponse.entityToDtoMapper()
                    .apply(rental.get()))
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRental(CreateRentalRequest request) {
        if(request.getName() == null ||
                request.getEstablishDate() == null ||
                request.getCity() == null ||
                request.getNumOfEmployees() == 0){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            LocalDate.parse(request.getEstablishDate());
        }catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<Rental> rentals = service.findAll();
        long newRentalId;
        if (!rentals.isEmpty()){
            newRentalId = Collections.max(rentals
                    .stream()
                    .map(Rental::getId)
                    .collect(Collectors.toList())) + INDEX_DB;
        }else {
            newRentalId = NEW_DB_RECORD_INDEX;
        }
        Rental rental = CreateRentalRequest.dtoToEntityMapper().apply(request);
        rental.setId(newRentalId);
        service.create(rental);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRentals(UpdateRentalsRequest request) {
        List<Rental> rentals = service.findAll();
        UpdateRentalsRequest.listDtoToListEntityUpdater().apply(rentals, request);
        service.updateAll(rentals);
        equipmentService.deleteAll();
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRental(@PathParam("id") Long id, UpdateRentalRequest request){
        if(request.getEstablishDate() != null) {
            try {
                LocalDate.parse(request.getEstablishDate());
            }catch (DateTimeParseException e){
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        if(request.getNumOfEmployees() < 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Optional<Rental> rental = service.find(id);
        if (rental.isPresent()) {
            UpdateRentalRequest.dtoToEntityUpdater().apply(rental.get(), request);
            service.update(rental.get());
            return Response.status(Response.Status.ACCEPTED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    public Response deleteRentals(){
        service.deleteAll();
        equipmentService.deleteAll();
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteRental(@PathParam("id") Long id){
        Optional<Rental> rental = service.find(id);
        if (rental.isPresent()) {
            service.delete(id);
            equipmentService.deleteByRental(id);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
