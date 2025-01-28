package com.cptkagan.notesapp.notesapp_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cptkagan.models.Account;
import com.cptkagan.models.Note;
import com.cptkagan.repositories.NoteRepository;
import com.cptkagan.services.NoteService;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    public void testGetNoteById(){
        // Arrange
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Test Title");
        note.setBody("Test Body");

        Mockito.when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        // Act
        Optional<Note> result = noteService.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(note.getTitle(), result.get().getTitle());
        assertEquals(note.getBody(), result.get().getBody());
    }

    @Test
    public void testGetAll(){
        // Arrange
        Note note1 = new Note();
        Note note2 = new Note();
        note1.setTitle("Test Title 1");
        note1.setBody("Test Body 1");

        note2.setTitle("Test Title 2");
        note2.setBody("Test Body 2");

        Mockito.when(noteRepository.findAll()).thenReturn(List.of(note1,note2));

        // Act
        List<Note> result = noteService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(note1.getTitle(), result.get(0).getTitle());
        assertEquals(note1.getBody(), result.get(0).getBody());
        assertEquals(note2.getTitle(), result.get(1).getTitle());
        assertEquals(note2.getBody(), result.get(1).getBody());
    }

    @Test
    public void testDelete(){
        // Arrange
        Note note1 = new Note();
        note1.setId(1L);

        // Act
        noteService.delete(note1);

        // Assert
        Mockito.verify(noteRepository, Mockito.times(1)).delete(note1);
    }

    @Test
    public void testGetNotesOfAccount(){
        // Arrange
        Account account = new Account();
        account.setId(1L);

        Note note1 = new Note();
        note1.setTitle("Test Title 1");
        note1.setBody("Test Body 1");
        note1.setAccount(account);

        Note note2 = new Note();
        note2.setTitle("Test Title 2");
        note2.setBody("Test Body 2");

        Note note3 = new Note();
        note3.setTitle("Test Title 3");
        note3.setBody("Test Body 3");
        note3.setAccount(account);

        Mockito.when(noteRepository.findByAccountId(1L)).thenReturn(List.of(note1,note3));

        // Act
        List<Note> result = noteService.getNotesOfAccount(1L);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(note1.getTitle(), result.get(0).getTitle());
        assertEquals(note1.getBody(), result.get(0).getBody());
        assertEquals(note3.getTitle(), result.get(1).getTitle());
        assertEquals(note3.getBody(), result.get(1).getBody());
    }

    @Test
    public void testGetNotesOfAccount_NoNotes(){
        // Arrange
        Mockito.when(noteRepository.findByAccountId(1L)).thenReturn(Collections.emptyList());

        // Act
        List<Note> result = noteService.getNotesOfAccount(1L);

        // Arrange
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
