package org.acme.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class JobTypeDTO {

    private ObjectId idJobType;

    @NotBlank(message = "Titulo Obrigatorio!")
    private String title;

    @NotBlank(message = "Descrição Obrigatorio!")
    private String description;

    private Boolean isActive;
    private LocalDateTime updateDate;
    private LocalDateTime inclusionDate;
    private LocalDateTime desactivation;
    private ObjectId idUpdateUser;
    private ObjectId idInclusionUser;
    private ObjectId idDeleteUser;
}
