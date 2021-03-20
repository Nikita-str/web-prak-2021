package DAO.StdImpl;

import DAO.Interfaces.I_BookExHistoryDAO;
import models.BookExHistory;
import utils.SQL_FuncCall;
import utils.SessionHelper;

import java.util.List;

public class StdDAO_BookExHistory implements I_BookExHistoryDAO {
    @Override public List<BookExHistory> GetExBookHistory(int bk_ex_id)
    { return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.GetExBookHistory(ses, bk_ex_id), BookExHistory.class); }

    @Override public List<BookExHistory> GetReaderHistory(int lib_card_id)
    { return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.GetReaderHistory(ses, lib_card_id), BookExHistory.class); }
}
