package com.cptkagan.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cptkagan.enums.Role;
import com.cptkagan.models.Account;
import com.cptkagan.models.Note;
import com.cptkagan.repositories.NoteRepository;

@Service
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private AccountService accountService;

    public Optional<Note> getById(Long id){
        return noteRepository.findById(id);
    }

    public List<Note> getAll(){
        return noteRepository.findAll();
    }

    public void delete(Note note){
        noteRepository.delete(note);
    }

    public Note save(Note note){
        if(note.getId()==null){
            if(note.getCreatedAt() == null){
                note.setCreatedAt(LocalDateTime.now());
            }
        }
        return noteRepository.save(note);
    }

    public List<Note> getNotesOfAccount(Long accountId){
        return noteRepository.findByAccountId(accountId);
    }

    public ResponseEntity<List<Note>> getAllNotes(String authName){
        Account account = accountService.findByUsername(authName);
        if(account == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Note> notes = noteRepository.findByAccountId(account.getId());
        if(notes.isEmpty()){
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).build();
        }
        return ResponseEntity.ok(notes);
    }

    public ResponseEntity<?> getAdminNotes(){
        List<Note> adminNotes = noteRepository.findAll();
        if(adminNotes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(adminNotes);
    }

    public ResponseEntity<Optional<Note>> getNote(Long id, String authName){
        Account account = accountService.findByUsername(authName);
        if(account != null){
            Optional<Note> note = noteRepository.findById(id);
            if(!note.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Note myNote = note.get();
            if(!account.equals(myNote.getAccount())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.ok(note);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<?> editNote(Long id, Note note, String authName) {
        Account account = accountService.findByUsername(authName);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Note> notes = noteRepository.findById(id);
        if (!notes.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Note noteToEdit = notes.get();
        if (!(account.equals(noteToEdit.getAccount()) || account.getRole() == Role.ADMIN )) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        noteToEdit.setTitle(note.getTitle());
        noteToEdit.setBody(note.getBody());
        noteToEdit.setEditedAt(LocalDateTime.now());
        noteRepository.save(noteToEdit);

        return ResponseEntity.ok()
                .body(Map.of("message", "Note Edited Successfully!", "editedNote", noteToEdit));
    }

    public ResponseEntity<?> deleteNote(Long id, String authName) {
        Account account = accountService.findByUsername(authName);
        if(account == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Note> notes = noteRepository.findById(id);
        if(!notes.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Note note = notes.get();
        if(!(account.getRole()==Role.ADMIN || note.getAccount().equals(account))){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        noteRepository.delete(note);
        return ResponseEntity.ok()
                .body(Map.of("message","Note Deleted Successfully", "deleteNote", note));
    }

}
