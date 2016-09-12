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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class NoteServiceTest {
    private NoteService service = new NoteService();

    @Test
    public void add() {
        String text = "Note #1";

        Note note = service.add(text);

        assertEquals(1, note.getId());
        assertEquals(text, note.getText());
    }

    @Test
    public void get_withoutNotes() {
        Collection<Note> notes = service.get();

        assertTrue(notes.isEmpty());
    }

    @Test
    public void get_withNote() {
        List<Note> expected = new ArrayList<>();
        String text = "Note #1";

        expected.add(service.add(text));

        assertEquals(expected, service.get());
    }

    @Test
    public void get_withNotes() {
        List<Note> expected = new ArrayList<>();
        String[] notes = new String[]{
                "Note #1",
                "Note #2",
                "Note #3",
                "Note #4"
        };

        for (String note : notes) {
            expected.add(service.add(note));
        }

        assertEquals(expected, service.get());
    }
}
