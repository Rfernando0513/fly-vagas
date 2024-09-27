package org.acme.DTO;

import lombok.*;
import org.acme.Enum.RegimeEnum;
import org.acme.Model.JobTypeModel;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {

    private ObjectId idJob;
    private ObjectId idJobType;

    private String title;
    private String location;
    private LocalTime startTime;
    private LocalTime exitTime;
    private Long salaryInCents;

    private Boolean isPne;
    private RegimeEnum regime;
    private String description;
    private Boolean isActive;
    //teste
    private LocalDate startDate;
    @BsonProperty("endDate")
    private LocalDate endDate;
    private LocalDateTime updateDate;
    private LocalDateTime inclusionDate;

    private ObjectId idUserUpdate;
    private ObjectId idUserInclusion;
    private ObjectId idUserExclusion;
    private LocalDateTime desactivateDate;

}
