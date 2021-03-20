package DAO.Interfaces;

import models.BookExHistory;
import models.BookExamples;
import models.Readers;

import java.sql.Date;
import java.util.List;

public interface I_ReadersDAO {
    public Integer AddReaders(String f_name, String s_name, String patr, String address, String phone_number);
    public Readers GetReadersById(Integer id);

    public Boolean ReaderHasBookEx(BookExamples book_ex, Readers reader);
    public Boolean ReaderHasBookEx(Integer bk_ex_id, Readers reader);
    public Boolean ReaderHasBookEx(BookExamples book_ex, Integer _lib_card_id);
    public Boolean ReaderHasBookEx(Integer bk_ex_id, Integer _lib_card_id);

    public List<BookExHistory> GetReaderHistory(Readers reader);
    public List<BookExHistory> GetReaderHistory(Integer _lib_card_id);

    public List<BookExHistory> GetReaderCurBook(Readers reader);
    public List<BookExHistory> GetReaderCurBook(Integer _lib_card_id);

    public List<BookExHistory> GetReaderOverdueBook(Readers reader, Boolean only_cur);
    public List<BookExHistory> GetReaderOverdueBook(Integer _lib_card_id, Boolean only_cur);

    public Boolean ReaderCanPassLibCard(Readers reader);
    public Boolean ReaderCanPassLibCard(Integer _lib_card_id);

    public void BookTake(Integer bk_ex_id, Integer lib_card_id, Date date_issue, Date schedule_ret_date);
    public void BookTake(Integer bk_ex_id, Integer lib_card_id, Date schedule_ret_date);
    public void BookTake(Integer book_ex_id, Integer lib_card_id, Integer day_for_ret);

    public void BookRet(Integer bk_ex_id);

    public List<Readers> FindReader_Surname(String surname);
    public List<Readers> FindReader(String first_name, String second_name);
    public List<Readers> FindReader(String first_name, String second_name, String patr);
    public List<Readers> FindReader_PhoneNumber(String phone_number);
}
