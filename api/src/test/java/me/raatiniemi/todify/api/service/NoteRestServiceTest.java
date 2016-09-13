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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class NoteRestServiceTest {
    private static final int PER_PAGE = 20;

    private NoteRepository noteRepository;
    private NoteRestService service;

    @Before
    public void setUp() {
        noteRepository = mock(NoteRepository.class);
        service = new NoteRestService(noteRepository);
    }

    @Test
    public void add() {
        Note note = new Note("Note #1");

        service.add(note);

        verify(noteRepository).save(note);
    }

    @Test
    public void get() {
        int page = 0;
        Pageable pageable = new PageRequest(page, PER_PAGE);
        when(noteRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        service.get(page, PER_PAGE);

        verify(noteRepository).findAll(pageable);
    }

    @Test
    public void get_secondPage() {
        int page = 1;
        Pageable pageable = new PageRequest(page, PER_PAGE);
        when(noteRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        service.get(page, PER_PAGE);

        verify(noteRepository).findAll(pageable);
    }

    @Test
    public void get_singular() {
        service.get(1);

        verify(noteRepository).findOne(eq(1L));
    }
}
