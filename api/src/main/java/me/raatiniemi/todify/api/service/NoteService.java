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

import java.util.ArrayList;
import java.util.Collection;

class NoteService {
    private Collection<Note> notes = new ArrayList<>();

    Note add(String text) {
        Note note = new Note(text);
        notes.add(note);

        return note;
    }

    Collection<Note> get() {
        return notes;
    }
}