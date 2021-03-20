package DAO.Interfaces;

import models.BookExHistory;
import java.util.List;

public interface I_BookExHistoryDAO {
    public List<BookExHistory> GetExBookHistory(int bk_ex_id);
    public List<BookExHistory> GetReaderHistory(int lib_card_id);
}
