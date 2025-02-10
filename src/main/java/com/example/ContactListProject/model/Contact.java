package com.example.ContactListProject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Setter
@Getter
@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name="contacts")
public class Contact {

    @Id
    @UuidGenerator
    @Column(name = "id",unique = true,updatable = false)
    private String id;
    private String name;

    @Email
    private String email ;
    private String title;
    private String phoneNumber;


    private String address;
    private String status;
    private String photoUrl;


}
