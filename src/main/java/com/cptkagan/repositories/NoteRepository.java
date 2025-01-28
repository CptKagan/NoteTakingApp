package com.cptkagan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cptkagan.models.Note;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByAccountId(Long accountId);
}
