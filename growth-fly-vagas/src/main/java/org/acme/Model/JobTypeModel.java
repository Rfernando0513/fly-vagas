package org.acme.Model;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "jobType")
public class JobTypeModel extends PanacheMongoEntity {

    private ObjectId idJobType;
    private String title;
    private String description;
    private Boolean isActive;
    private LocalDateTime updateDate;
    private LocalDateTime inclusionDate;
    private LocalDateTime desactivation;
    private ObjectId idUpdateUser;
    private ObjectId idInclusionUser;
    private ObjectId idDeleteUser;

}
