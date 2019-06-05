package com.wugui.dao;


import com.google.common.collect.ImmutableSet;
import com.wugui.Application;
import com.wugui.model.Author;
import com.wugui.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@ActiveProfiles({"pg"})// 选择激活的profile
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class AuthorDaoTest {


    @Autowired
    private AuthorDao authorDao;

    @Test
    public void testSave() {
        Author author = new Author();
        author.setName("鲁迅");
        Book book = new Book();
        book.setName("进击的巨人");
        Book book2 = new Book();
        book2.setName("风之子");
        Book book3 = new Book();
        book3.setName("闰土先生");

        ImmutableSet<Book> books = ImmutableSet.of(book, book2, book3);
        author.setBooks(books);

        authorDao.save(author);
    }

    @Test
    public void testSelect() {
        Iterable<Author> authorDaoAll = authorDao.findAll();
        System.out.println(authorDaoAll);
    }
}