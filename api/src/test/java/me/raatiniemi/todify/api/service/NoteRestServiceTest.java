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

import com.fasterxml.jackson.databind.ObjectMapper;
import me.raatiniemi.todify.api.model.Note;
import me.raatiniemi.todify.api.repository.NoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class NoteRestServiceTest {
    private static final MediaType expectedContentType;

    static {
        expectedContentType = new MediaType(
                MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8")
        );
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private NoteRepository noteRepository;
    private List<Note> notes = new ArrayList<>();

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(context).build();

        noteRepository.deleteAllInBatch();

        notes = IntStream.of(1, 2)
                .mapToObj(i -> new Note("Note " + i))
                .map(note -> noteRepository.save(note))
                .collect(Collectors.toList());
    }

    @Test
    public void add() throws Exception {
        mockMvc.perform(post("/todo").contentType(expectedContentType)
                .content(json(new Note("Note #3"))))
                .andExpect(status().isCreated());
    }

    private String json(Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(o);
    }

    @Test
    public void get_readSingleNote() throws Exception {
        Note note = notes.get(0);

        mockMvc.perform(get("/todo/" + note.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(expectedContentType))
                .andExpect(jsonPath("$.id", is((int) note.getId())))
                .andExpect(jsonPath("$.text", is(note.getText())));
    }

    @Test
    public void get_readNotes() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/todo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(expectedContentType))
                .andExpect(jsonPath("$", hasSize(notes.size())));

        for (Note note : notes) {
            String prefix = "$[" + notes.indexOf(note) + "]";

            resultActions.andExpect(jsonPath(prefix + ".id", is((int) note.getId())))
                    .andExpect(jsonPath(prefix + ".text", is(note.getText())));
        }
    }
}
