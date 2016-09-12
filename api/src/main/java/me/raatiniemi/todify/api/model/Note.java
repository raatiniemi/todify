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

import java.util.Objects;

public class Note {
    private long id;
    private String text;

    public Note(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Note(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Note)) {
            return false;
        }

        Note note = (Note) o;
        return getId() == note.getId()
                && Objects.equals(getText(), note.getText());

    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + Objects.hashCode(getText());

        return result;
    }
}
