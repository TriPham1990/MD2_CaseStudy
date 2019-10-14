package com.codegym.controllers;

import com.codegym.model.Note;
import com.codegym.model.NoteType;
import com.codegym.service.NoteService;
import com.codegym.service.NoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class NoteController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteTypeService noteTypeService;

    @ModelAttribute("noteTypes")
    private Page<NoteType> noteTypes(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        return noteTypeService.finAll(pageable);
    }

    @GetMapping(value = "/home")
    public ModelAndView listNote(@RequestParam Optional<String> search,
                                 @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        Page<Note> notes;
        if (search.isPresent()) {
            notes = noteService.findAllByNoteTypeDescriptionContaining(search.get(), pageable);
        } else {
            notes = noteService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("note/home");
        modelAndView.addObject("notes", notes);
        return modelAndView;
    }

    @GetMapping(value = "/create-note")
    public ModelAndView showCreateNoteForm() {
        ModelAndView modelAndView = new ModelAndView("note/create");
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @PostMapping(value = "/create-note")
    public String createNote(@Validated @ModelAttribute Note note) {
        noteService.save(note);
        return "redirect:/create-note";
    }

    @GetMapping(value = "/edit-note/{id}")
    public ModelAndView showEditNoteForm(@PathVariable Long id) {
        Note note = noteService.findById(id);
        ModelAndView modelAndView = new ModelAndView("note/edit");
        modelAndView.addObject("note", note);
        return modelAndView;
    }

    @PostMapping(value = "/edit-note")
    public ModelAndView editNote(@Validated @ModelAttribute Note note) {
        noteService.save(note);
        ModelAndView modelAndView = new ModelAndView("note/edit");
        modelAndView.addObject("note", note);
        modelAndView.addObject("message", "Note updated successfully");
        return modelAndView;
    }

    @GetMapping(value = "/delete-note/{id}")
    public ModelAndView showDeleteNoteForm(@PathVariable Long id) {
        Note note = noteService.findById(id);
        ModelAndView modelAndView = new ModelAndView("note/delete");
        modelAndView.addObject("note", note);
        return modelAndView;
    }

    @PostMapping(value = "/delete-note")
    public String deleteNote(@ModelAttribute Note note, RedirectAttributes redirect) {
        noteService.remove(note.getId());
        redirect.addFlashAttribute("message", "Note remove successfully");
        return "redirect:/home";
    }
}
