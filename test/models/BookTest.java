package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void createBook() {

        final Book book = new Book(1, "My Title");

        assertTrue(book.getId() == 1);
        assertTrue(book.getTile().equals("My Title"));

    }

    @Test
    public void setTitle() {

        final Book book = new Book(1, "My Title");

        assertTrue(book.getTile().equals("My Title"));

        book.setTile("Something else");

        assertEquals(book.getTile(), "Something else");

    }
}