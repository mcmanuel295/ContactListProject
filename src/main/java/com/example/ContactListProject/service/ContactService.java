package com.example.ContactListProject.service;

import com.example.ContactListProject.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ContactService {
    Page<Contact> getAllContacts(int page,int size);

    Contact getContact(String id);

    Contact createContact(Contact contact);

    void deleteContact(Contact contact);

    String uploadPhoto(String userID, MultipartFile file);
}
