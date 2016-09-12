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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class NoteRestServiceTest {
    private NoteRepository noteRepository;
    private NoteRestService service;

    @Before
    public void setUp() {
        noteRepository = mock(NoteRepository.class);
        service = new NoteRestService(noteRepository);
    }

    @Test
    public void add() {
        String text = "Note #1";

        service.add(text);

        verify(noteRepository).save(new Note(text));
    }

    @Test
    public void get() {
        service.get();

        verify(noteRepository).findAll();
    }

    @Test
    public void get_singular() {
        service.get(1);

        verify(noteRepository).findOne(eq(1L));
    }
}
