package com.swisscom.api.model;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource implements Serializable {
    @Id
    private String id;
    private List<Owner> owners;
}