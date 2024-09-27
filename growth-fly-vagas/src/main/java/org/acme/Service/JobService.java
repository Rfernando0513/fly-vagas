package org.acme.Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.acme.DTO.JobDTO;
import org.acme.Model.JobModel;
import org.acme.Model.JobTypeModel;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@ApplicationScoped
public class JobService {

    @Transactional
    public JobDTO createJob(JobDTO jobDTO) {
        JobModel existingJob = JobModel.find("title", jobDTO.getTitle()).firstResult();
        if (existingJob != null) {
            throw new IllegalArgumentException(String.format("Essa vaga já existe com o título: %s, use outro",
                                                                                            jobDTO.getTitle()));
        }

        try {
            JobModel jobModel = new JobModel();

            JobTypeModel jobTypeModel = JobTypeModel.findById(jobDTO.getIdJobType());
            if (jobTypeModel == null) {
                throw new IllegalArgumentException("JobType não encontrado.");
            }

            jobModel.setIdJob(jobDTO.getIdJob());
            jobModel.setIdJobType(jobTypeModel);
            jobModel.setTitle(jobDTO.getTitle());
            jobModel.setLocation(jobDTO.getLocation());
            jobModel.setStartTime(jobDTO.getStartTime());
            jobModel.setExitTime(jobDTO.getExitTime());
            jobModel.setSalaryInCents(jobDTO.getSalaryInCents());
            jobModel.setIsPne(jobDTO.getIsPne());
            jobModel.setRegime(jobDTO.getRegime());
            jobModel.setDescription(jobDTO.getDescription());
            jobModel.setIsActive(jobDTO.getIsActive());
            jobModel.setStartDate(LocalDate.from(jobDTO.getStartDate()));
            jobModel.setEndDate(jobDTO.getEndDate());
            jobModel.setInclusionDate(LocalDateTime.now());


            jobModel.setIdUserInclusion(jobDTO.getIdUserInclusion());

            jobModel.persist();

            jobDTO.setIdJob(jobModel.getIdJob());
            jobDTO.setIdUserInclusion(jobModel.getIdUserInclusion());

            return jobDTO;
        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar nova vaga: " + e);
        }
    }

    public List<JobModel> findAll(Integer page, Integer pageSize) {
        try {
            if (page < 0) page = 0;
            if (pageSize <= 0) pageSize = 10;

            List<JobModel> jobs = JobModel.findAll()
                    .page(page, pageSize)
                    .list();

            return jobs;
        } catch (Exception e) {
            log.error("Erro ao buscar vagas: ", e);
            throw new RuntimeException("Falha ao buscar vagas." + e);
        }
    }

    public  JobDTO findById(Object idJob){
        JobModel jobModel = JobModel.findById(idJob);
        if (jobModel == null) {
            throw new IllegalArgumentException("Vaga não encontrado.");
        }

        try {

            return convertToDTO(jobModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public JobDTO updateJob(Object idJob, JobDTO jobDTO) {
        JobModel jobModel = JobModel.findById(idJob);

        if (jobModel == null) {
            throw new IllegalArgumentException("Vaga não encontrado.");
        }

        JobTypeModel jobTypeModel = JobTypeModel.findById(jobDTO.getIdJobType());
        if (jobTypeModel == null) {
            throw new IllegalArgumentException("Tipo de vaga não encontrado.");
        }

        jobModel.setIdJob(jobDTO.getIdJob());
        jobModel.setIdJobType(jobTypeModel);
        jobModel.setTitle(jobDTO.getTitle());
        jobModel.setLocation(jobDTO.getLocation());
        jobModel.setStartTime(jobDTO.getStartTime());
        jobModel.setExitTime(jobDTO.getExitTime());
        jobModel.setSalaryInCents(jobDTO.getSalaryInCents());
        jobModel.setIsPne(jobDTO.getIsPne());
        jobModel.setRegime(jobDTO.getRegime());
        jobModel.setDescription(jobDTO.getDescription());
        jobModel.setIsActive(jobDTO.getIsActive());

        if (jobDTO.getStartDate() != null) {
            jobModel.setStartDate(LocalDate.from(jobDTO.getStartDate()));
        }

        jobModel.setUpdateDate(LocalDateTime.now());
        jobModel.setIdUserUpdate(jobDTO.getIdUserUpdate());

        jobModel.update();

        jobDTO.setIdJob(jobModel.getIdJob());
        jobDTO.setIdUserInclusion(jobModel.getIdUserInclusion());

        return jobDTO;
    }

    public Object desactiveJob(ObjectId idJob, JobDTO jobDTO) {
        JobModel jobModel = JobModel.findById(idJob);
        if(jobModel == null){
            throw new IllegalArgumentException("Vaga não encontrado para o ID: " + jobModel.getIdJob());
        }

        if(jobModel.getIsActive() == false){
            throw new IllegalArgumentException("Vaga já desativado: " + jobModel.getTitle());
        }

        jobModel.setIsActive(false);
        jobDTO.setIdUserExclusion(jobModel.getIdUserExclusion());

        jobModel.update();

        return convertToDTO(jobModel);
    }

    public Object activeJob(ObjectId idJob, JobDTO jobDTO) {
        JobModel jobModel = JobModel.findById(idJob);
        if(jobModel == null){
            throw new IllegalArgumentException("Vaga não encontrado para o ID: " + jobModel.getIdJob());
        }

        if(jobModel.getIsActive() == true){
            throw new IllegalArgumentException("Vaga já está ativa: " + jobModel.getTitle());
        }

        jobModel.setIsActive(true);
        jobDTO.setIdUserExclusion(jobModel.getIdUserExclusion());

        jobModel.update();

        return convertToDTO(jobModel);
    }

    private JobDTO convertToDTO(JobModel jobModel) {
        JobDTO jobDTO = new JobDTO();
        JobTypeModel jobTypeModel = new JobTypeModel();

        jobDTO.setIdJob(jobModel.id);
        jobDTO.setIdJobType(jobTypeModel.id);
        jobDTO.setTitle(jobModel.getTitle());
        jobDTO.setLocation(jobModel.getLocation());
        jobDTO.setStartTime(jobModel.getStartTime());
        jobDTO.setExitTime(jobModel.getExitTime());
        jobDTO.setSalaryInCents(jobModel.getSalaryInCents());
        jobDTO.setIsPne(jobModel.getIsPne());
        jobDTO.setRegime(jobModel.getRegime());
        jobDTO.setDescription(jobModel.getDescription());
        jobDTO.setIsActive(jobModel.getIsActive());
        jobDTO.setStartDate(jobModel.getStartDate());
        jobDTO.setEndDate(jobModel.getEndDate());
        jobDTO.setUpdateDate(jobModel.getUpdateDate());
        jobDTO.setInclusionDate(jobModel.getInclusionDate());
        jobDTO.setIdUserUpdate(jobModel.getIdUserUpdate());
        jobDTO.setIdUserInclusion(jobModel.getIdUserInclusion());
        jobDTO.setIdUserExclusion(jobModel.getIdUserExclusion());
        jobDTO.setDesactivateDate(jobModel.getDesactivateDate());

        return jobDTO;
    }
}
