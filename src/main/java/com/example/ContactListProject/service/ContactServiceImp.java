package com.example.ContactListProject.service;

import com.example.ContactListProject.model.Contact;
import com.example.ContactListProject.repository.ContactRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.example.ContactListProject.constant.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Transactional(rollbackOn = Exception.class )
public class ContactServiceImp implements  ContactService{
    private final ContactRepository repo;


    public ContactServiceImp(ContactRepository repo) {
        this.repo = repo;
    }

    @Override
    public Page<Contact> getAllContacts(int page, int size) {
       return  repo.findAll(PageRequest.of(page,size, Sort.by("name")));
    }

    @Override
    public Contact getContact(String id) {
        return  repo.findById(id).orElseThrow(()-> new RuntimeException("Contact Not Found!!!"));
    }

    @Override
    public Contact createContact(Contact contact) {
        return repo.save(contact);
    }

    @Override
    public void deleteContact(Contact contact) {
        repo.deleteById(contact.getId());
    }

    @Override
    public String uploadPhoto(String userId, MultipartFile file){
        Contact contact =getContact(userId);
        String photoUrl = photoFunction.apply(userId,file);
        contact.setPhotoUrl(photoUrl);
        repo.save(contact);

        return photoUrl;
    }


    private final Function<String,String> fileExtension= filename ->
            Optional
            .of( filename)
                    .filter(name -> name.contains("."))
                    .map(name -> "."+name.substring(filename.lastIndexOf(".") +1))
                    .orElse(".png");


    private final BiFunction<String,MultipartFile,String> photoFunction= (userId, image)->{
        String filename = userId+fileExtension.apply(image.getOriginalFilename());
//        public static final String PHOTO_DIRECTORY =System.getProperty("user.home")+ "/Downloads/uploads";

        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();

            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }

            Files.copy(image.getInputStream(),fileStorageLocation.resolve(filename ),REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/contacts/image/"+userId+fileExtension.apply(image.getOriginalFilename())).toUriString();

        }
        catch (Exception ex){
            throw new RuntimeException("Unable to save image");
        }
    };


}
