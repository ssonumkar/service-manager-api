package com.swisscom.api.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Document(collection = "resources")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Resource implements Serializable {

    @Id
    private String id;

    @NotBlank(message = "Service ID must not be blank")
    private String serviceId;
}