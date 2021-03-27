
import DAO.Interfaces.I_AuthorsDAO;
import DAO.Interfaces.I_BooksDAO;
import DAO.Interfaces.I_PublishersDAO;
import DAO.StdImpl.StdDAO_Factory;
import models.Books;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import utils.DatabaseHelper;

public class TEST_Books {

    @BeforeClass
    public void Before() throws Exception {
        if(!DatabaseHelper.DataBaseClear()) throw new Exception();
    }

    @Test
    public void TestBooks() {
        StdDAO_Factory factory = StdDAO_Factory.getInstance();
        I_BooksDAO book_dao = factory.getBookDao();
        I_AuthorsDAO a_dao = factory.getAuthorDao();
        I_PublishersDAO pub_dao = factory.getPublisherDao();

        Books book_1 = book_dao.GetBookById(book_dao.AddBook("book with only name"));

        book_dao.AddAuthorToBook(a_dao.GetAuthor("author", "no name", null), book_1);
        book_dao.AddAuthorToBook(a_dao.GetAuthor("автор", "безымянный", null), book_1);
        book_dao.AddAuthorToBook(a_dao.GetAuthor("автор", "безымянный", null), book_1);
        book_dao.AddAuthorToBook(a_dao.GetAuthor("автор", "безымянный", null), book_1);
        factory.getAuthorDao().AddAuthorToBook(a_dao.GetAuthor("автор", "безымянный", null), book_1);
        factory.getAuthorDao().AddAuthorToBook(a_dao.GetAuthor("333", "333", "333"), book_1);

        Assert.assertEquals(book_dao.GetAuthorOfBook(book_1).size(), 3);
        Assert.assertEquals(factory.getAuthorDao().GetAuthorOfBook(book_1).size(), 3);

        book_dao.AddAuthorToBook(
                a_dao.GetAuthorId("Станислав", "Лем", "Герман"),
                book_dao.AddBook("Возвращение со звезд", "...",
                        "АСТ", 1961, "978-5-17-093614-4", 10)
        );

        book_dao.AddAuthorToBook(
                a_dao.GetAuthorId("Станислав", "Лем", "Герман"),
                book_dao.AddBook("Фиаско", "последний роман Станислава Лема", "АСТ", 1986, "978-5-17-069299-6", 4)
        );

        int b_id = book_dao.AddBook("Сумма технологий", "Классика футурологии.",
                                    book_dao.BookFind_Title("Возвращение со звезд").get(0).getPublisher().getPId(),
                            1964, "978-5-17-100637-2", 2);
        book_dao.AddAuthorToBook(a_dao.GetAuthorId("Станислав", "Лем", "Герман"),  b_id);

        Assert.assertEquals(book_dao.BookFind_Title("Сумма технологий").get(0).getBookId(), b_id);
        Assert.assertEquals(book_dao.BookFind_Title("сумма технологий").get(0).getBookId(), b_id);

        Assert.assertEquals(book_dao.GetAuthorOfBook(book_dao.BookFind_Title("сумма технологий").get(0)).size(), 1);

        Assert.assertEquals(
                book_dao.GetAuthorOfBook(book_dao.BookFind_Title("сумма технологий").get(0)).get(0).getAuthorId(),
                book_dao.GetAuthorOfBook(book_dao.BookFind_Title("Возвращение со звезд").get(0)).get(0).getAuthorId()
        );

        Assert.assertEquals(
                book_dao.BookFind_Title("сумма технологий").get(0).getPublisher().getPId(),
                book_dao.BookFind_Title("Фиаско").get(0).getPublisher().getPId()
        );

        Books book_2 = book_dao.GetBookById(book_dao.AddBook("book with year", null, 1964, null, 3));

        Assert.assertEquals(book_dao.BookFind_Title("book").size(), 2);
        Assert.assertEquals(book_dao.BookFind_Isbn("978-51").size(), 3);
        Assert.assertEquals(book_dao.BookFind_Title("суМма").get(0).getBookId(), b_id);
        Assert.assertEquals(book_dao.BookFind_Year(1964).size(), 2);
        Assert.assertEquals(book_dao.BookFind_Publisher("АСТ").size(), 3);
        Assert.assertEquals(book_dao.BookFind(null, null, 1964, "978-51", false).size(), 1);

        Assert.assertEquals(book_dao.BookFind(null, pub_dao.GetPublisherId("АСТ"), 1964, null, false).size(), 1);

        book_dao.AddBookEx(book_2, 2);
        Assert.assertEquals(book_dao.GetBookEx(book_2).size(), 5);
        book_dao.AddBookEx(book_2, 5);
        Assert.assertEquals(book_dao.GetBookEx(book_2).size(), 10);
        Assert.assertEquals(book_dao.GetBookEx(book_2).get(0).getBook().getBookId(), book_2.getBookId());

        Assert.assertEquals(book_dao.BookFind_All().size(), 5);

        book_dao.BookDereg(book_2, false);
        Assert.assertEquals(book_dao.GetBookEx(book_2).size(), 0);
        Assert.assertEquals(book_dao.GetBookEx(book_2, true, false).size(), 10);

        int b_new_id = book_dao.AddBook("not real", "...", 999, null, 5);
        int amount_not_decom_book = book_dao.BookFind_All().size();
        Assert.assertEquals(book_dao.BookFind_Year(999).size(), 1);
        book_dao.BookDereg(b_new_id, false);
        Assert.assertEquals(book_dao.BookFind_Year(999).size(), 0);
        Assert.assertEquals(amount_not_decom_book - 1, book_dao.BookFind_All().size());

    }
}
