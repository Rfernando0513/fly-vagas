package org.acme.Model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.acme.Enum.RegimeEnum;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection = "Job")
public class JobModel extends PanacheMongoEntity {

    private ObjectId idJob;
    private JobTypeModel idJobType;

    private String title;
    private String location;
    private LocalTime startTime;
    private LocalTime exitTime;
    private Long salaryInCents;
    private Boolean isPne;
    private RegimeEnum regime;
    private String description;
    private Boolean isActive;

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
