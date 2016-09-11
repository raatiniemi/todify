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

package me.raatiniemi.todify.api.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(Parameterized.class)
public class NoteEqualsHashCodeTest {
    private String message;
    private Boolean expected;
    private Note note;
    private Object compareTo;

    public NoteEqualsHashCodeTest(String message, Boolean expected, Note note, Object compareTo) {
        this.message = message;
        this.expected = expected;
        this.note = note;
        this.compareTo = compareTo;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        Note note = new Note("Note #1");

        return Arrays.asList(
                new Object[][]{
                        {
                                "With the same instance",
                                Boolean.TRUE,
                                note,
                                note
                        },
                        {
                                "With null",
                                Boolean.FALSE,
                                note,
                                null
                        },
                        {
                                "With incompatible type",
                                Boolean.FALSE,
                                note,
                                ""
                        },
                        {
                                "With different text",
                                Boolean.FALSE,
                                note,
                                new Note("Note #2")
                        },
                        {
                                "With same text",
                                Boolean.TRUE,
                                note,
                                new Note("Note #1")
                        }
                }
        );
    }

    @Test
    public void equals() {
        if (shouldBeEqual()) {
            assertEqual();
            return;
        }

        assertNotEqual();
    }

    private Boolean shouldBeEqual() {
        return expected;
    }

    private void assertEqual() {
        assertTrue(message, note.equals(compareTo));

        validateHashCodeWhenEqual();
    }

    private void validateHashCodeWhenEqual() {
        assertTrue(message, note.hashCode() == compareTo.hashCode());
    }

    private void assertNotEqual() {
        assertFalse(message, note.equals(compareTo));

        validateHashCodeWhenNotEqual();
    }

    private void validateHashCodeWhenNotEqual() {
        if (null == compareTo) {
            return;
        }
        assertFalse(message, note.hashCode() == compareTo.hashCode());
    }
}
