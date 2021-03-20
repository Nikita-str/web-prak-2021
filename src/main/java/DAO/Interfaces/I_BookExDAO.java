package DAO.Interfaces;

import models.BookExHistory;
import java.util.List;

public interface I_BookExDAO {
    public void BookExDereg(int bk_ex_id, boolean need_to_ret);
    public List<BookExHistory> GetExBookHistory(int bk_ex_id);

    public boolean BookIsDereg(int bk_ex_id);
    public boolean BookAlreadyTaked(int bk_ex_id);
    public boolean TheSameBook(int ex_id_1, int ex_id_2);
    public boolean ReaderHasBookEx(int bk_ex_id, int _lib_card_id);
}
