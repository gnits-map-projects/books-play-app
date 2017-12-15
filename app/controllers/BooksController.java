package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Book;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;

public class BooksController extends Controller {

    private JPAApi jpaApi;

    @Inject
    public BooksController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result createBook() {

        final JsonNode jsonNode = request().body().asJson();
        final String title = jsonNode.get("title").asText();

        if (null == title) {
            return badRequest("Missing title");
        }

        final Book book = new Book();
        book.setTile(title);

        jpaApi.em().persist(book);

        return created(book.getId().toString());

    }

    @Transactional
    public Result getBookById(Integer id) {

        final Book book = jpaApi.em().find(Book.class, id);
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

        final Book book = jpaApi.em().find(Book.class, id);
        if (null == book) {
            return notFound("Did not find book with id " + id);
        }

        book.setTile(newTitle);

        jpaApi.em().persist(book);

        final JsonNode bookJson = Json.toJson(book);

        return ok(bookJson);

    }

    @Transactional
    public Result deleteBookById(Integer id) {

        final Book book = jpaApi.em().find(Book.class, id);
        if (null == book) {
            return notFound("Did not find book with id " + id);
        }

        jpaApi.em().remove(book);

        return noContent();

    }

    @Transactional
    public Result getAllBooks() {

        TypedQuery<Book> query = jpaApi.em().createQuery("SELECT b FROM Book b", Book.class);
        List<Book> books = query.getResultList();

        final JsonNode jsonNode = Json.toJson(books);

        return ok(jsonNode);
    }

}
