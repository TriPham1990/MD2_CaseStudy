package com.codegym.controllers;

import com.codegym.model.NoteType;
import com.codegym.service.NoteService;
import com.codegym.service.NoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteTypeController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteTypeService noteTypeService;

    @GetMapping(value = "/descriptions")
    public ModelAndView listNoteType(@PageableDefault(size = 5, sort = "id") Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("note-type/home");
        modelAndView.addObject("noteTypes", noteTypeService.finAll(pageable));
        return modelAndView;
    }

    @GetMapping(value = "/create-description")
    public ModelAndView showCreateNoteTypeForm() {
        ModelAndView modelAndView = new ModelAndView("note-type/create");
        modelAndView.addObject("noteType", new NoteType());
        return modelAndView;
    }

    @PostMapping(value = "/create-description")
    public String createNoteType(@Validated @ModelAttribute NoteType noteType) {
        noteTypeService.save(noteType);
        return "redirect:/create-description";
    }

    @GetMapping(value = "/edit-description/{id}")
    public ModelAndView showEditNoteTypeForm(@PathVariable Long id) {
        NoteType noteType = noteTypeService.findById(id);
        ModelAndView modelAndView = new ModelAndView("note-type/edit");
        modelAndView.addObject("noteType", noteType);
        return modelAndView;
    }

    @PostMapping(value = "/edit-description")
    public ModelAndView editNoteType(@Validated @ModelAttribute NoteType noteType) {
        noteTypeService.save(noteType);
        ModelAndView modelAndView = new ModelAndView("note-type/edit");
        modelAndView.addObject("noteType", noteType);
        modelAndView.addObject("message", "Note Type Update Successfully");
        return modelAndView;
    }

    @GetMapping(value = "/delete-description/{id}")
    public ModelAndView showDeleteNoteTypeForm(@PathVariable Long id) {
        NoteType noteType = noteTypeService.findById(id);
        ModelAndView modelAndView = new ModelAndView("note-type/delete");
        modelAndView.addObject("noteType", noteType);
        return modelAndView;
    }

    @PostMapping(value = "/delete-description")
    public String deleteNoteType(@ModelAttribute NoteType noteType, RedirectAttributes redirect) {
        noteTypeService.remove(noteType.getId());
        redirect.addFlashAttribute("message", "Note Type Remove Successfully");
        return "redirect:/descriptions";
    }
}
