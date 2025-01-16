package com.example.ContactListProject.repository;

import com.example.ContactListProject.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact,String> {
    Optional<Contact> findById(String id);
}
