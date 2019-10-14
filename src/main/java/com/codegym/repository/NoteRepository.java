package com.codegym.repository;

import com.codegym.model.Note;
import com.codegym.model.NoteType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {
    Page<Note> findAllByNoteTypeDescriptionContaining(String description, Pageable pageable);
}
