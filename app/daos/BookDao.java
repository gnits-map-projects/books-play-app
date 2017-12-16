package daos;

import models.Book;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;

public class BookDao implements CrudDao<Integer, Book> {

    private JPAApi jpaApi;

    @Inject
    public BookDao(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public Book persist(Book book) {

        jpaApi.em().persist(book);

        return book;
    }

    public Book deleteById(Integer id) {

        final Book book = findById(id);
        if (null == book) {
            return null;
        }

        jpaApi.em().remove(book);

        return book;
    }

    public Book findById(Integer id) {

        final Book book = jpaApi.em().find(Book.class, id);
        return book;

    }

    public Book update(Book book) {

        final Book existingBook = findById(book.getId());
        if (null == existingBook) {
            return null;
        }

        existingBook.setTile(book.getTile());
        persist(existingBook);

        return existingBook;
    }

    public List<Book> findAll() {

        TypedQuery<Book> query = jpaApi.em().createQuery("SELECT b FROM Book b", Book.class);
        List<Book> books = query.getResultList();

        return books;
    }

}
