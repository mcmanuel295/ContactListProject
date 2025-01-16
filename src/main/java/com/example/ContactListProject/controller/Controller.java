package com.example.ContactListProject.controller;

import com.example.ContactListProject.model.Contact;
import com.example.ContactListProject.service.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.ContactListProject.constant.Constant.PHOTO_DIRECTORY;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/contacts")
public class Controller {

    private ContactService service;

    public Controller(ContactService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact){

        return ResponseEntity.created(URI.create("/contacts/{user ID}")).body(service.createContact(contact));
    }

    @GetMapping
    public ResponseEntity<Page<Contact>> getContacts(@RequestParam(value ="page" ,defaultValue = "0") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size){

        return ResponseEntity.ok().body(service.getAllContacts(page,size));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Contact> getContact(@PathVariable String userId){

        return ResponseEntity.ok().body(service.getContact(userId));
    }
    

    @PutMapping("/{userId}/upload")
    public ResponseEntity<String> uploadPhoto(@RequestParam("userId") String userId,
                                        @RequestParam("file") MultipartFile file
                                        ){

        return ResponseEntity.ok().body(service.uploadPhoto(userId,file));
    }

    @GetMapping(value = "/image/{filename}", produces = {IMAGE_PNG_VALUE,IMAGE_JPEG_VALUE})
    public byte[] getPhoto(@RequestParam("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY+filename));

    }
}
