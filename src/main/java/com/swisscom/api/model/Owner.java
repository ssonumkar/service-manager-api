package com.swisscom.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Document(collection = "owners")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Owner implements Serializable {

    @Id
    private String id;

    @NotBlank(message = "Service ID must not be blank")
    private String serviceId;
    @NotBlank(message = "Resource ID must not be blank")
    private String resourceId;
    private String name;
    private String accountNumber;
    private int level;
}
