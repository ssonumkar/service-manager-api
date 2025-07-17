package com.swisscom.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Owner implements Serializable {
    @Id
    private String id;
    private String name;
    private String accountNumber;
    private int level;
}
