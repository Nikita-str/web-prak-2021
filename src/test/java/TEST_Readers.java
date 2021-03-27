
import DAO.Interfaces.*;
import DAO.StdImpl.StdDAO_Factory;
import models.BookExHistory;
import models.BookExamples;
import models.Books;

import models.Readers;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import utils.DatabaseHelper;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class TEST_Readers {

    @BeforeClass
    public void Before() throws Exception {
        if(!DatabaseHelper.DataBaseClear()) throw new Exception();
    }

    @Test
    public void TestReaders() {
        // + tests for [book ex history]

        StdDAO_Factory factory = StdDAO_Factory.getInstance();
        I_BooksDAO book_dao = factory.getBookDao();
        I_BookExDAO ex_dao = factory.getBookExDao();
        I_ReadersDAO reader_dao = factory.getReaderDao();

        Readers r0 = reader_dao.GetReadersById(reader_dao.AddReaders("name", "surname", "patr", "adr", "8(16)32-64"));
        Readers r1 = reader_dao.GetReadersById(reader_dao.AddReaders("F", "surname", null, null, "5(555)-30-30-0-0-0"));
        Readers r2 = reader_dao.GetReadersById(reader_dao.AddReaders("no me", "surname", null, "adr", "8(16)8-16"));

        Assert.assertEquals(reader_dao.FindReader_PhoneNumber("8-16-32").size(), 1);
        Assert.assertEquals(reader_dao.FindReader_PhoneNumber("8(16").size(), 2);

        Assert.assertEquals(reader_dao.FindReader_Surname("surname").size(), 3);
        Assert.assertEquals(reader_dao.FindReader("name", "surname").size(), 1);
        Assert.assertEquals(reader_dao.FindReader("name", "surname", "patr").size(), 1);

        Books book = book_dao.GetBookById(book_dao.AddBook("fiction"));
        book_dao.AddBookEx(book, 5);

        Books book_2 = book_dao.GetBookById(book_dao.AddBook("f"));
        book_dao.AddBookEx(book_2, 3);

        Assert.assertFalse(reader_dao.ReaderHasBookEx(book_dao.GetBookEx(book).get(0), r0));
        Assert.assertTrue(reader_dao.ReaderCanPassLibCard(r0));
        reader_dao.BookTake(book_dao.GetBookEx(book).get(0).getBookExId(), r0.getLibraryCardId(), 10);
        Assert.assertFalse(reader_dao.ReaderCanPassLibCard(r0));
        reader_dao.BookTake(book_dao.GetBookEx(book_2).get(0).getBookExId(), r0.getLibraryCardId(), 10);
        Assert.assertTrue(reader_dao.ReaderHasBookEx(book_dao.GetBookEx(book).get(0), r0));

        List<BookExHistory> cur_r0 = reader_dao.GetReaderCurBook(r0);
        for(int i =0; i < cur_r0.size(); i++) reader_dao.BookRet(cur_r0.get(i).getBookEx().getBookExId());
        Assert.assertTrue(reader_dao.ReaderCanPassLibCard(r0));

        Assert.assertEquals(reader_dao.GetReaderOverdueBook(r0, false).size(), 0);
        BookExamples ex = book_dao.GetBookEx(book).get(0);

        Calendar c = Calendar.getInstance();
        c.set(2021, 2, 5);
        Date d_is = new Date(c.getTime().getTime());
        c.set(2021, 2, 8);
        Date d_ret = new Date(c.getTime().getTime());

        int ex_history_len = factory.getBookExHistoryDao().GetExBookHistory(ex.getBookExId()).size();
        reader_dao.BookTake(ex.getBookExId(), r0.getLibraryCardId(), d_is, d_ret);
        Assert.assertEquals(factory.getBookExHistoryDao().GetExBookHistory(ex.getBookExId()).size(), ex_history_len + 1);
        ex_history_len += 1;

        Assert.assertEquals(reader_dao.GetReaderOverdueBook(r0, true).size(), 1);
        reader_dao.BookRet(ex.getBookExId());
        Assert.assertEquals(factory.getBookExHistoryDao().GetExBookHistory(ex.getBookExId()).size(), ex_history_len);

        Assert.assertEquals(reader_dao.GetReaderOverdueBook(r0, true).size(), 0);
        Assert.assertEquals(reader_dao.GetReaderOverdueBook(r0, false).size(), 1);

        Assert.assertTrue(reader_dao.ReaderCanPassLibCard(r0));
        c.set(2021, 11, 9);
        Date date_ret = new Date(c.getTime().getTime());
        reader_dao.BookTake(ex.getBookExId(), r0.getLibraryCardId(), date_ret);
        Assert.assertFalse(reader_dao.ReaderCanPassLibCard(r0));
        reader_dao.BookRet(reader_dao.GetReaderCurBook(r0).get(0).getBookEx().getBookExId());
        Assert.assertTrue(reader_dao.ReaderCanPassLibCard(r0));

        Assert.assertEquals(reader_dao.GetReaderHistory(r0).size(), 4);
        Assert.assertEquals(factory.getBookExHistoryDao().GetReaderHistory(r0.getLibraryCardId()).size(), 4);
        Assert.assertEquals(reader_dao.GetReaderHistory(r1).size(), 0);
        Assert.assertNotEquals(factory.getBookExHistoryDao().GetExBookHistory(ex.getBookExId()).size(), 0);
    }
}
