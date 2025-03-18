package com.pranjal.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.pranjal.util.Constants.*;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {


    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception;

    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getNotes(@PathVariable Integer id) throws Exception;


    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllNotes();


    @GetMapping("/search")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> searchNotes(@RequestParam String key  , @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNo, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);


    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getAllUserNotes(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNo, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);


    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;


    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;


    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getUserRecycleBinNotes();

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

    @DeleteMapping("/delete-recycle-bin")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> emptyRecycleBin();


    @GetMapping("/fav/{noteId}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;

    @DeleteMapping("/un-fav/{favNotTd}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNotTd) throws Exception;

    @GetMapping("/fav-note")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getUser();

    @GetMapping("/copy/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
}
