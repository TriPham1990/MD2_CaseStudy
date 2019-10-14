package com.codegym.service;

import com.codegym.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {

    Page<Note> findAllByNoteTypeDescriptionContaining(String description, Pageable pageable);

    Page<Note> findAll(Pageable pageable);

    Note findById(Long id);

    void save(Note note);

    void remove(Long id);
}
