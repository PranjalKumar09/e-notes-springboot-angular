package com.pranjal.endpoint;

import com.pranjal.dto.NotesDto;
import com.pranjal.dto.NotesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.pranjal.util.Constants.*;
@Tag(name = "Notes Management", description = "Operations for managing user notes")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

    @Operation(
            summary = "Save a new note",
            description = "Saves a note with optional file attachment. Requires USER role.",
            tags = {"Notes Management"}
    )
    @PostMapping(value = "/", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> saveNotes(@RequestParam @Parameter(description = "Json String Notes", required = true, content = @Content(schema = @Schema(implementation = NotesRequest.class))) String notes, @RequestParam(required = false) MultipartFile file) throws Exception;

    @Operation(
            summary = "Download a note",
            description = "Retrieves a note by its ID. Accessible to ADMIN and USER roles.",
            tags = {"Notes Management"}
    )
    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<?> getNotes(@PathVariable Integer id) throws Exception;

    @Operation(
            summary = "Get all notes",
            description = "Fetches all notes. Requires ADMIN role.",
            tags = {"Notes Management"}
    )
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getAllNotes();

    @Operation(
            summary = "Search notes",
            description = "Search notes using a keyword with pagination support. Requires USER role.",
            tags = {"Notes Management"}
    )
    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> searchNotes(@RequestParam String key,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNo,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);

    @Operation(
            summary = "Get all user notes",
            description = "Retrieves all notes created by the user with pagination. Requires USER role.",
            tags = {"Notes Management"}
    )
    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getAllUserNotes(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNo,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);

    @Operation(
            summary = "Soft delete a note",
            description = "Moves a note to the recycle bin instead of deleting it permanently. Requires USER role.",
            tags = {"Notes Management"}
    )
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(
            summary = "Restore a deleted note",
            description = "Restores a previously deleted note from the recycle bin. Requires USER role.",
            tags = {"Notes Management"}
    )
    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;

    @Operation(
            summary = "Get notes from recycle bin",
            description = "Retrieves all notes currently in the user’s recycle bin. Requires USER role.",
            tags = {"Notes Management"}
    )
    @GetMapping("/recycle-bin")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getUserRecycleBinNotes();

    @Operation(
            summary = "Hard delete a note",
            description = "Permanently deletes a note. Requires USER role.",
            tags = {"Notes Management"}
    )
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;

    @Operation(
            summary = "Empty recycle bin",
            description = "Permanently deletes all notes in the recycle bin. Requires USER role.",
            tags = {"Notes Management"}
    )
    @DeleteMapping("/delete-recycle-bin")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> emptyRecycleBin();

    @Operation(
            summary = "Mark note as favorite",
            description = "Marks a note as a favorite. Requires USER role.",
            tags = {"Notes Management"}
    )
    @GetMapping("/fav/{noteId}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;

    @Operation(
            summary = "Unmark note from favorite",
            description = "Removes a note from favorites. Requires USER role.",
            tags = {"Notes Management"}
    )
    @DeleteMapping("/un-fav/{favNotTd}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNotTd) throws Exception;

    @Operation(
            summary = "Get all favorite notes",
            description = "Retrieves all user’s favorite notes. Requires USER role.",
            tags = {"Notes Management"}
    )
    @GetMapping("/fav-note")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> getUser();

    @Operation(
            summary = "Copy a note",
            description = "Creates a copy of the note with the specified ID. Requires USER role.",
            tags = {"Notes Management"}
    )
    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
}

