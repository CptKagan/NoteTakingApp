package com.cptkagan.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cptkagan.models.Account;
import com.cptkagan.models.Note;
import com.cptkagan.services.AccountService;
import com.cptkagan.services.NoteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@Controller
public class NoteController {
    
    @Autowired
    private NoteService noteService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/note/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addNoteHandler(@Valid @RequestBody Note note, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.badRequest().body(errors);
        }

        if (principal != null) {
            String authUser = principal.getName();
            Account account = accountService.findByUsername(authUser);
            note.setAccount(account);
        }

        noteService.save(note);
        return ResponseEntity.ok("Note added successfully!");
    }

    @GetMapping("/notes")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Note>> getNotes(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String authName = principal.getName();

        return noteService.getAllNotes(authName);
    }

    @GetMapping("/note/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> singleNoteView(@PathVariable Long id, Principal principal){
        if(principal == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String authName = principal.getName();

        return noteService.getNote(id, authName);
    }

    @PutMapping("note/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody Note note, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String authName = principal.getName();
        ResponseEntity<?> response = noteService.editNote(id, note, authName);

        return response;
    }

    @GetMapping("/admin/notes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes() {
        return noteService.getAdminNotes();
    }
    
    @DeleteMapping("/note/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteNote(@PathVariable Long id, Principal principal){
        if(principal == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String authName = principal.getName();
        ResponseEntity<?> response = noteService.deleteNote(id,authName);


        return response;
    }

}
