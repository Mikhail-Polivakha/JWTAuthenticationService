package com.example.JWTAuthenticationService.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Column(name = "created")
    private Date dateOfCreation;

    @LastModifiedDate
    @Column(name = "updated")
    private Date lastUpdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ActiveStatus activeStatus;
}
