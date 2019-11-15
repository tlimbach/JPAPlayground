/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testabcd;

import com.mycompany.testabcd.authorbooks.Author;
import com.mycompany.testabcd.authorbooks.Book;
import com.mycompany.testabcd.parking.EntityHelper;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author thorsten
 */
public class AuthorBookTest {

    public AuthorBookTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getSummary method, of class Todo.
     */
    @Test
    public void testTest() {

        EntityHelper.clearTable("book");
        EntityHelper.clearTable("author");

        Author author = new Author("a1");
        EntityHelper.persist(author);

        Book book1 = new Book("book1");
        EntityHelper.persist(book1);

        book1.setAuthor(author);
        EntityHelper.merge(book1);

        author.getBooks().add(book1);
        EntityHelper.merge(author);

        //   assertThat(author.getBooks(), IsEmptyCollection.empty());
        author = (Author) EntityHelper.update(author.getClass(), author.getId());

        assertThat(author.getBooks().size(), is(1));

    }

    @Test
    public void testLoadBooksForAuthor() {

        EntityHelper.clearTable("book");
        EntityHelper.clearTable("author");

        Author author = new Author("a1");
        author = (Author) EntityHelper.persist(author);
        Long authorId = author.getId();

        for (int t = 0; t < 10; t++) {
            Book book1 = new Book("book" + t);
            book1.setAuthor(author);
            //  EntityHelper.persist(book1);

            author.getBooks().add(book1);
        }
        EntityHelper.merge(author);

        //EntityHelper.clearCache();
        Author authorFromDB = (Author) EntityHelper.update(Author.class, authorId);

        assertThat(authorFromDB.getBooks().size(), is(10));
    }

    @Test
    public void testDeleteBooksForAuthor() {

        EntityHelper.clearTable("book");
        EntityHelper.clearTable("author");

        Author author = new Author("a1");
        author = (Author) EntityHelper.persist(author);
        Long authorId = author.getId();

        for (int t = 0; t < 10; t++) {
            Book book1 = new Book("book" + t);
            book1.setAuthor(author);
            //  EntityHelper.persist(book1);

            author.getBooks().add(book1);
        }
        EntityHelper.merge(author);

        //EntityHelper.clearCache();
        Author authorFromDB = (Author) EntityHelper.update(Author.class, authorId);

        assertThat(authorFromDB.getBooks().size(), is(10));

        // Books from Denise -> nothing
        EntityManager em = EntityHelper.getEntitymanager();
        TypedQuery<Book> queryBooks = em.createQuery("select b from Book b INNER JOIN b.author a where a.name like '%denise%'", Book.class);
        List<Book> booksFromDenise = queryBooks.getResultList();

        assertThat(booksFromDenise, IsEmptyCollection.emptyCollectionOf(Book.class));

        // Books from a1 -> all 10 Books
        String realAuthorName = author.getName();

        queryBooks = em.createQuery("select b from Book b INNER JOIN b.author a where a.name like '%" + realAuthorName + "%'", Book.class);
        List<Book> booksFromTheAutor = queryBooks.getResultList();

        assertThat(booksFromTheAutor.size(), is(10));

        // getAuthorName for Books named "book4"
        TypedQuery<Author> queryAuthor = em.createQuery("select a from Author a INNER JOIN a.books b where b.name LIKE '%book4%'", Author.class);
        Author singleResult = queryAuthor.getSingleResult();

        assertThat(singleResult.getName(), is("a1"));

        queryBooks = em.createQuery("select b from Book b where b.name LIKE 'book5'", Book.class);
        Book book5 = queryBooks.getSingleResult();

        assertNotNull(book5);

        // delete book5
        EntityHelper.delete(book5.getClass(), book5.getId());
    }

}
