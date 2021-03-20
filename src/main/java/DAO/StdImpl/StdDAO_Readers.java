package DAO.StdImpl;
import DAO.Interfaces.I_ReadersDAO;
import DAO.Interfaces.StdImpl_ReaderDAO;
import models.BookExHistory;
import models.Readers;
import utils.SQL_FuncCall;
import utils.SessionHelper;

import java.sql.Date;
import java.util.List;

public class StdDAO_Readers extends StdImpl_ReaderDAO implements I_ReadersDAO {
    @Override
    public Integer AddReaders(String f_name, String s_name, String patr, String address, String phone_number) {
        return SessionHelper.InSessionActWithR(ses -> SQL_FuncCall.AddReader(ses, f_name, s_name, patr, address, phone_number));
    }

    @Override public Readers GetReadersById(Integer id) { return SessionHelper.InSessionActWithR(ses -> ses.load(Readers.class, id)); }

    @Override public Boolean ReaderHasBookEx(Integer bk_ex_id, Integer _lib_card_id) {
        return SessionHelper.InSessionActWithR(ses -> SQL_FuncCall.ReaderHasBookEx(ses, bk_ex_id, _lib_card_id));
    }

    @Override public List<BookExHistory> GetReaderHistory(Integer _lib_card_id) {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.GetReaderHistory(ses, _lib_card_id), BookExHistory.class);
    }

    @Override public List<BookExHistory> GetReaderCurBook(Integer _lib_card_id) {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.GetReaderCurBook(ses, _lib_card_id), BookExHistory.class);
    }

    @Override public List<BookExHistory> GetReaderOverdueBook(Integer _lib_card_id, Boolean only_cur) {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.GetReaderOverdueBook(ses, _lib_card_id, only_cur), BookExHistory.class);
    }

    @Override public Boolean ReaderCanPassLibCard(Integer _lib_card_id) {
        return SessionHelper.InSessionActWithR(ses -> SQL_FuncCall.ReaderCanPassLibCard(ses, _lib_card_id));
    }

    @Override public void BookTake(Integer bk_ex_id, Integer lib_card_id, Date date_issue, Date schedule_ret_date) {
        SessionHelper.InSessionAct(ses -> SQL_FuncCall.BookTake(ses, bk_ex_id, lib_card_id, date_issue, schedule_ret_date));
    }

    @Override public void BookTake(Integer bk_ex_id, Integer lib_card_id, Date schedule_ret_date) {
        SessionHelper.InSessionAct(ses -> SQL_FuncCall.BookTake(ses, bk_ex_id, lib_card_id, schedule_ret_date));
    }

    @Override public void BookTake(Integer book_ex_id, Integer lib_card_id, Integer day_for_ret) {
        SessionHelper.InSessionAct(ses -> SQL_FuncCall.BookTake(ses, book_ex_id, lib_card_id, day_for_ret));
    }

    @Override public void BookRet(Integer bk_ex_id) { SessionHelper.InSessionAct(ses -> SQL_FuncCall.BookRet(ses, bk_ex_id)); }
}
