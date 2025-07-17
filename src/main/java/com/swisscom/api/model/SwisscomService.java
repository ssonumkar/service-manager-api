package com.swisscom.api.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.List;

@Document("services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwisscomService implements Serializable {
    @Id
    private String id;
    private List<Resource> resources;
}