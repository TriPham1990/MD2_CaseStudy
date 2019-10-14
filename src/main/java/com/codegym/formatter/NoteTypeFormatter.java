package com.codegym.formatter;

import com.codegym.model.NoteType;
import com.codegym.service.NoteTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class NoteTypeFormatter implements Formatter<NoteType> {

    @Autowired
    public NoteTypeService noteTypeService;

    @Autowired
    public NoteTypeFormatter(NoteTypeService noteTypeService) {
        this.noteTypeService = noteTypeService;
    }

    @Override
    public NoteType parse(String text, Locale locale) throws ParseException {
        return noteTypeService.findById(Long.parseLong(text));
    }

    @Override
    public String print(NoteType object, Locale locale) {
        return "[" + object.getId() + "," + object.getDescription() + "]";
    }
}
