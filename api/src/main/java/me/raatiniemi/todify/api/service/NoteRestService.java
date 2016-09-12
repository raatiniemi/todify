/*
 * Copyright (C) 2016 Tobias Raatiniemi
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.raatiniemi.todify.api.service;

import me.raatiniemi.todify.api.model.Note;
import me.raatiniemi.todify.api.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class NoteRestService {
    private NoteRepository noteRepository;

    NoteRestService(@Autowired NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/todo")
    Note add(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todo")
    List<Note> get() {
        return noteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todo/{id}")
    Note get(@PathVariable("id") long id) {
        return noteRepository.findOne(id);
    }
}
