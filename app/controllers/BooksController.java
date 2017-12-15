package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Book;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BooksController extends Controller {

    private Map<Integer, Book> books = new HashMap<>();

    private Integer index = 0;

    public Result createBook() {

        final JsonNode jsonNode = request().body().asJson();
        final String title = jsonNode.get("title").asText();

        if (null == title) {
            return badRequest("Missing title");
        }

        final Integer index = this.index++;

        final Book book = new Book(index, title);

        books.put(index, book);

        return created(index.toString());

    }

    public Result getBookById(Integer id) {

        final Book book = books.get(id);
        if (null == book) {
            return notFound("Did not find book with id " + id);
        }

        final JsonNode jsonNode = Json.toJson(book);

        return ok(jsonNode);

    }

    public Result updateBookById(Integer id) {

        final JsonNode jsonNode = request().body().asJson();
        final String newTitle = jsonNode.get("title").asText();

        if (null == newTitle) {
            return badRequest("Missing title");
        }

        final Book book = books.get(id);
        if (null == book) {
            return notFound("Did not find book with id " + id);
        }

        book.setTile(newTitle);

        books.put(id, book);

        final JsonNode bookJson = Json.toJson(book);

        return ok(bookJson);

    }

    public Result deleteBookById(Integer id) {

        final Book deletedBook = books.remove(id);
        if (null == deletedBook) {
            return notFound("Did not find book with id " + id);
        }

        return noContent();

    }

    public Result getAllBooks() {

        final Collection<Book> books = this.books.values();

        final JsonNode jsonNode = Json.toJson(books);

        return ok(jsonNode);
    }

}
