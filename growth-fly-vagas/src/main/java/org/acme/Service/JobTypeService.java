package org.acme.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.acme.DTO.JobTypeDTO;
import org.acme.Model.JobTypeModel;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class JobTypeService {

    @Transactional
    public JobTypeDTO createJobyType( JobTypeDTO jobTypeDTO){
        JobTypeModel existingJobType = JobTypeModel.find("title", jobTypeDTO.getTitle()).firstResult();
        if (existingJobType != null) {
            throw new IllegalArgumentException(String.format("Tipo de vaga já existe com o titulo: %s, use outro",
                                                                                                jobTypeDTO.getTitle()));
        }

        try {
            JobTypeModel jobTypeModel = new JobTypeModel();

            jobTypeModel.setIdJobType(jobTypeDTO.getIdJobType());
            jobTypeModel.setTitle(jobTypeDTO.getTitle());
            jobTypeModel.setDescription(jobTypeDTO.getDescription());
            jobTypeModel.setIsActive(true);
            jobTypeModel.setInclusionDate(LocalDateTime.now());
            jobTypeModel.setIdInclusionUser(jobTypeDTO.getIdInclusionUser());

            jobTypeModel.persist();

            return jobTypeDTO;

        }catch (Exception e){
            throw new IllegalArgumentException("Falha ao cadastrar o usuario: " + e);
        }
    }

    public List<JobTypeDTO> findAll(Integer page, Integer pageSize) {
        List<JobTypeModel> jobTypes = JobTypeModel.findAll()
                .page(page, pageSize)
                .list();

        return jobTypes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public JobTypeDTO findById(ObjectId idJobType) {

        JobTypeModel jobTypeModel = JobTypeModel.findById(idJobType);
        if (jobTypeModel == null) {
            throw new IllegalArgumentException("Tipo de vaga não encontrado!");
        }

        return toDTO(jobTypeModel);
    }

    @Transactional
    public JobTypeDTO updateJobType(ObjectId idJobType, JobTypeDTO jobTypeDTO) {
        JobTypeModel jobTypeModel = JobTypeModel.findById(idJobType);
        if (jobTypeModel == null) {
            throw new IllegalArgumentException("Tipo de vaga não encontrado para o ID: " + idJobType);
        }

        jobTypeModel.setTitle(jobTypeDTO.getTitle());
        jobTypeModel.setDescription(jobTypeDTO.getDescription());
        jobTypeModel.setIsActive(jobTypeDTO.getIsActive());
        jobTypeModel.setUpdateDate(LocalDateTime.now());
        jobTypeModel.setIdUpdateUser(jobTypeDTO.getIdUpdateUser());

        jobTypeModel.update();

        return toDTO(jobTypeModel);
    }

    @Transactional
    public JobTypeDTO desactiveJob(ObjectId idJobType, JobTypeDTO jobTypeDTO){
        JobTypeModel jobTypeModel = JobTypeModel.findById(idJobType);
        if(jobTypeModel == null){
            throw new IllegalArgumentException("Tipo de vaga não encontrado para o ID: " + jobTypeModel.getIdJobType());
        }

        if(jobTypeModel.getIsActive() == false){
            throw new IllegalArgumentException("Tipo de vaga já desativado: " + jobTypeModel.getTitle());
        }

        jobTypeDTO.setIsActive(false);
        jobTypeDTO.setIdDeleteUser(jobTypeModel.getIdDeleteUser());
        jobTypeDTO.setDesactivation(jobTypeModel.getDesactivation());

        jobTypeModel.update();

        return  toDTO(jobTypeModel);
    }

    @Transactional
    public JobTypeDTO activeJob(ObjectId idJobType, JobTypeDTO jobTypeDTO){
        JobTypeModel jobTypeModel = JobTypeModel.findById(idJobType);
        if (jobTypeModel == null || jobTypeModel.getIsActive() != null && jobTypeModel.getIsActive()) {
            throw new IllegalArgumentException("Tipo de vaga não encontrado ou já está ativo para o ID: " + jobTypeDTO.getIdJobType());
        }

        jobTypeModel.setIsActive(true);
        jobTypeModel.setIdDeleteUser(jobTypeDTO.getIdDeleteUser());
        jobTypeModel.setDesactivation(jobTypeDTO.getDesactivation());

        jobTypeModel.update();

        return  toDTO(jobTypeModel);
    }



    private JobTypeDTO toDTO(JobTypeModel jobTypeModel) {
        return new JobTypeDTO(
                jobTypeModel.id,
                jobTypeModel.getTitle(),
                jobTypeModel.getDescription(),
                jobTypeModel.getIsActive(),
                jobTypeModel.getUpdateDate(),
                jobTypeModel.getInclusionDate(),
                jobTypeModel.getDesactivation(),
                jobTypeModel.getIdUpdateUser(),
                jobTypeModel.getIdInclusionUser(),
                jobTypeModel.getIdDeleteUser()
        );
    }

}
