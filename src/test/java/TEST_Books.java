
import DAO.Interfaces.I_AuthorsDAO;
import DAO.Interfaces.I_BooksDAO;
import DAO.Interfaces.I_PublishersDAO;
import DAO.StdImpl.StdDAO_Factory;
import models.Books;
import models.Publishers;

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

        Books book_1 = book_dao.GetBookById(book_dao.AddBook("book with only name"));

        book_dao.AddAuthorToBook(a_dao.GetAuthor("author", "no name", null), book_1);
        book_dao.AddAuthorToBook(a_dao.GetAuthor("автор", "безымянный", null), book_1);
        book_dao.AddAuthorToBook(a_dao.GetAuthor("автор", "безымянный", null), book_1);
        book_dao.AddAuthorToBook(a_dao.GetAuthor("автор", "безымянный", null), book_1);

        Assert.assertEquals(book_dao.GetAuthorOfBook(book_1).size(), 2);

        book_dao.AddAuthorToBook(
                a_dao.GetAuthorId("Станислав", "Лем", "Герман"),
                book_dao.AddBook("Возвращение со звезд", "...",
                        "АСТ", 1961, "978-5-17-093614-4", 10)
        );

        book_dao.AddAuthorToBook(
                a_dao.GetAuthorId("Станислав", "Лем", "Герман"),
                book_dao.AddBook("Фиаско", "последний роман Станислава Лема", "АСТ", 1986, "978-5-17-069299-6", 4)
        );

        int b_id = book_dao.AddBook("Сумма технологий", "Классика футурологии.", "АСТ", 1964, "978-5-17-100637-2", 2);
        book_dao.AddAuthorToBook(a_dao.GetAuthorId("Станислав", "Лем", "Герман"),  b_id);

        Assert.assertEquals(book_dao.BookFind_Title("Сумма технологий").get(0).getBookId(), b_id);
        Assert.assertEquals(book_dao.BookFind_Title("сумма технологий").get(0).getBookId(), b_id);

        Assert.assertEquals(book_dao.BookFind_Title("сумма технологий").get(0).getAuthors().size(), 1);

    }
}
