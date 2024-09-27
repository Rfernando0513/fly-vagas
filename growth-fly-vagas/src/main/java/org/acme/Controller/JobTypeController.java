package org.acme.Controller;

import io.netty.handler.codec.MessageAggregationException;
import io.quarkus.panache.common.Page;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.acme.DTO.JobTypeDTO;
import org.acme.Model.JobTypeModel;
import org.acme.Service.JobTypeService;
import org.bson.types.ObjectId;

import java.util.List;

@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/jobs_type")
public class JobTypeController {

    @Inject
    JobTypeService jobTypeService;

    @POST
    public Response createJobyType(@Valid JobTypeDTO jobTypeDTO){
        try {
            String message = String.format("Tipo de vaga: %s cadastrado com sucesso por: %s",
                    jobTypeDTO.getTitle(),
                    jobTypeDTO.getIdInclusionUser());

            return Response
                    .ok(jobTypeService.createJobyType(jobTypeDTO))
                    .entity(message)
                    .build();
        } catch (Exception e) {
            String message = String.format("Falha ao cadastrar o tipo de vaga: " + e);

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(message)
                    .build();
        }
    }

    @GET
    public Response findAllJobType(@QueryParam("page") @DefaultValue("0") Integer page,
                                    @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        try{
            return Response
                    .ok(jobTypeService.findAll(page, pageSize))
                    .build();
        } catch(Exception e) {
            throw new MessageAggregationException("erro ao listar tipos de vagas: " + e.getMessage());
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id")ObjectId idJobType){
        try{
            return Response
                    .ok(jobTypeService.findById(idJobType))
                    .build();
        }catch(Exception e){
            String message = String.format("Falha ao consultar tipos de vaga: " + e.getMessage());
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(message)
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateJobyType(@PathParam("id") ObjectId idJobType, JobTypeDTO jobTypeDTO) {
        try {
            String message = String.format("Tipo de vaga: %s atualizada com sucesso por: %s",
                                                                    jobTypeDTO.getTitle(),
                                                                    jobTypeDTO.getIdUpdateUser());
            return Response
                    .ok(jobTypeService.updateJobType(idJobType, jobTypeDTO))
                    .entity(message)
                    .build();
        } catch (Exception e) {
            String message = String.format("Falha ao atualizar tipo de vaga " + e.getMessage());

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(message)
                    .build();
        }
    }

    @PUT
    @Path("/{id}/desactivate")
    public Response desactivateJob(@PathParam("id") ObjectId idJobType, JobTypeDTO jobTypeDTO){
        try{
            String message = String.format("Tipo de vaga: %s desativada com sucesso por: %s",
                    jobTypeDTO.getTitle(),
                    jobTypeDTO.getIdUpdateUser());

            return Response
                    .ok(jobTypeService.desactiveJob(idJobType, jobTypeDTO))
                    .entity(message)
                    .build();
        } catch(Exception e){
            String message = String.format("Falha ao desativar tipo de vaga: " + e.getMessage());

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(message)
                    .build();
        }
    }

    @PUT
    @Path("/{id}/active")
    public Response activeJob(@PathParam("id") ObjectId idJobType, JobTypeDTO jobTypeDTO){
        try{
            String message = String.format("Tipo de vaga: %s ativada com sucesso por: %s",
                    jobTypeDTO.getTitle(),
                    jobTypeDTO.getIdUpdateUser());

            return Response
                    .ok(jobTypeService.activeJob(idJobType, jobTypeDTO))
                    .entity(message)
                    .build();
        } catch(Exception e){
            String message = String.format("Falha ao ativar tipo de vaga: " + e.getMessage());

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(message)
                    .build();
        }
    }
}
