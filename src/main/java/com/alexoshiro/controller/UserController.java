package com.alexoshiro.controller;

import com.alexoshiro.entity.UserEntity;
import com.alexoshiro.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    public Response getUsers(@QueryParam("page") @DefaultValue("0") Integer page,
                             @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        final var users = userService.findAll(page, pageSize);
        return Response.ok(users).build();
    }

    @Path("/{id}")
    @GET
    public Response getUser(@PathParam("id") UUID id) {
        return Response.ok(userService.findById(id)).build();
    }

    @POST
    public Response createUser(
            @Valid @NotNull(message = "Usuário não pode ser vazio")
            UserEntity userEntity) {
        return Response.ok(userService.createUser(userEntity)).build();
    }

    @Path("/{id}")
    @PUT
    public Response updateUser(@PathParam("id") UUID id,
                               @Valid
                               @NotNull(message = "Usuário não pode ser vazio")
                               UserEntity userEntity) {
        return Response.ok(userService.updateUser(id, userEntity)).build();
    }

    @Path("/{id}")
    @DELETE
    public Response deleteUser(@PathParam("id") UUID id) {
        userService.deleteById(id);
        return Response.noContent().build();
    }

}
