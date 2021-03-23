
import DAO.Interfaces.*;
import DAO.StdImpl.StdDAO_Factory;
import models.BookExamples;
import models.Books;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import utils.DatabaseHelper;

import java.util.List;

public class TEST_BookExs {

    @BeforeClass
    public void Before() throws Exception {
        if (!DatabaseHelper.DataBaseClear()) throw new Exception();
    }

    @Test
    public void TestBookExamples() {
        StdDAO_Factory factory = StdDAO_Factory.getInstance();
        I_BooksDAO book_dao = factory.getBookDao();
        I_AuthorsDAO a_dao = factory.getAuthorDao();
        I_ReadersDAO reader_dao = factory.getReaderDao();
        I_BookExDAO ex_dao = factory.getBookExDao();

        Books book = book_dao.GetBookById(book_dao.AddBook("fiction"));
        book_dao.AddBookEx(book, 5);

        List<BookExamples> exs = book_dao.GetBookEx(book);
        Assert.assertEquals(exs.size(), 5);
        BookExamples ex_0 = exs.get(0);
        BookExamples ex_1 = exs.get(1);
        Assert.assertFalse(ex_dao.BookAlreadyTaked(ex_0));
        Assert.assertFalse(ex_dao.BookIsDereg(ex_0));
        Assert.assertTrue(ex_dao.TheSameBook(ex_0, ex_1));

        int card_id = reader_dao.AddReaders("name", "surname", null, null, null);

        Assert.assertFalse(ex_dao.ReaderHasBookEx(ex_1.getBookExId(), card_id));

        reader_dao.BookTake(ex_0.getBookExId(), card_id, 30);
        Assert.assertTrue(ex_dao.ReaderHasBookEx(ex_1.getBookExId(), card_id));

        Assert.assertEquals(ex_dao.GetExBookHistory(ex_0).size(), 1);
        Assert.assertEquals(ex_dao.GetExBookHistory(ex_1).size(), 0);
    }
}
