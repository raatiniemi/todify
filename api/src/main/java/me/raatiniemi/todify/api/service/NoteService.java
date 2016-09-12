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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

@RestController
class NoteService {
    private AtomicLong incrementalCounter = new AtomicLong();
    private Collection<Note> notes = new ArrayList<>();

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/todo")
    Note add(@RequestBody String text) {
        Note note = new Note(incrementalCounter.incrementAndGet(), text);
        notes.add(note);

        return note;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todo")
    Collection<Note> get() {
        return notes;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/todo/{id}")
    Note get(@PathVariable("id") long id) {
        // TODO: if note is not present, return 404.
        return notes.stream()
                .filter(note -> id == note.getId())
                .findFirst()
                .orElse(null);
    }
}
