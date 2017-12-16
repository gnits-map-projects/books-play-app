package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import daos.BookDao;
import models.Book;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class BooksController extends Controller {

    private BookDao bookDao;

    @Inject
    public BooksController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Transactional
    public Result createBook() {

        final JsonNode jsonNode = request().body().asJson();
        final String title = jsonNode.get("title").asText();

        if (null == title) {
            return badRequest("Missing title");
        }

        Book book = new Book();
        book.setTile(title);

        book = bookDao.persist(book);

        return created(book.getId().toString());
    }

    @Transactional
    public Result getBookById(Integer id) {

        if (null == id) {
            return badRequest();
        }

        final Book book = bookDao.findById(id);
        if (null == book) {
            return notFound("Did not find book with id " + id);
        }

        final JsonNode jsonNode = Json.toJson(book);

        return ok(jsonNode);

    }

    @Transactional
    public Result updateBookById(Integer id) {

        final JsonNode jsonNode = request().body().asJson();
        final String newTitle = jsonNode.get("title").asText();
        if (null == newTitle) {
            return badRequest("Missing title");
        }

        Book book = new Book(id, newTitle);
        book = bookDao.update(book);

        final JsonNode bookJson = Json.toJson(book);
        return ok(bookJson);
    }

    @Transactional
    public Result deleteBookById(Integer id) {

        final Book book = bookDao.deleteById(id);
        if (null == book) {
            return notFound();
        }

        return noContent();

    }

    @Transactional
    public Result getAllBooks() {

        final List<Book> books = bookDao.findAll();

        final JsonNode jsonNode = Json.toJson(books);

        return ok(jsonNode);
    }

}
