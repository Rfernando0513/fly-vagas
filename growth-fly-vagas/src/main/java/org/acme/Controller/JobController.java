package org.acme.Controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.acme.DTO.JobDTO;
import org.acme.Model.JobModel;
import org.acme.Service.JobService;
import org.bson.types.ObjectId;

@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/jobs")
public class JobController {

    @Inject
    JobService jobService;

    @POST
    public Response createJob(@Valid JobDTO jobDTO){

        try{
            String message = String.format("Vaga: %s Cadastrada com Sucesso por: %s",
                                                                    jobDTO.getTitle(),
                                                         jobDTO.getIdUserInclusion());

            return Response
                    .ok(jobService.createJob(jobDTO))
                    .entity(message)
                    .build();
        }catch(Exception e){
            String message = String.format("Falha ao criar Vaga: %s: %s!",
                                                        jobDTO.getTitle(),
                                                            e.getMessage());

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(message)
                    .build();
        }

    }

    @GET
    public Response findAllJob(@QueryParam("page") @DefaultValue("0") Integer page,
                               @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        try {
            return Response
                    .ok(jobService.findAll(page, pageSize))
                    .build();
        } catch (Exception e) {
            String message = String.format("Falha ao buscar vagas: %s",
                                                        e.getMessage());

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(message)
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") ObjectId idJob){

        try{
            return Response
                    .ok(jobService.findById(idJob))
                    .build();
        }catch(Exception e){
            JobDTO jobDTO = new JobDTO();
            String message = String.format("Falha ao buscaer o Id: %s erro: s%" + jobDTO.getIdJob(),
                                                                                  e.getMessage());

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(message)
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateJob(@PathParam("id") ObjectId idJob, JobDTO jobDTO) {
        try {
            log.info("Atualizando vaga ID: {}, novos dados: {}", idJob, jobDTO);
            String message = String.format("Vaga atualizada com sucesso por usuário ID: %s",
                                                                jobDTO.getIdUserUpdate());
            return Response
                    .ok(jobService.updateJob(idJob, jobDTO))
                    .entity(message)
                    .build();
        } catch (Exception e) {
            log.error("Erro ao atualizar vaga ID {}: {}", idJob, e.getMessage());
            String message = String.format("Falha ao atualizar vaga ID '%s': %s",
                                                                        idJob,
                                                                        e.getMessage());
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(message)
                    .build();
        }
    }

    @PUT
    @Path("/desactive/{id}")
    public  Response desactiveJob(@PathParam("id") ObjectId idJob, JobDTO jobDTO){
        try {
            String message = String.format("Vaga %s desativada com sucesso por: %s ",
                                                                    jobDTO.getTitle(),
                                                            jobDTO.getIdUserExclusion());
            return Response
                    .ok(jobService.desactiveJob(idJob, jobDTO))
                    .entity(message)
                    .build();
        } catch (Exception e){
            String message = String.format("Falha ao desativar a vaga %s : %s",
                                                            jobDTO.getTitle(),
                                                            e.getMessage());

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(message)
                    .build();
        }
    }

    @PUT
    @Path("/active/{id}")
    public  Response activeJob(@PathParam("id") ObjectId idJob, JobDTO jobDTO){
        try {
            JobModel jobModel = new JobModel();
            String message = String.format("Vaga %s ativada com sucesso por: %s ",
                                                                jobModel.getTitle(),
                                                    jobDTO.getIdUserExclusion());
            return Response
                    .ok(jobService.activeJob(idJob, jobDTO))
                    .entity(message)
                    .build();
        } catch (Exception e){
            String message = String.format("Falha ao ativar a vaga %s : %s",
                                                            jobDTO.getTitle(),
                                                                e.getMessage(),
                                                                e);

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(message)
                    .build();
        }
    }
}
