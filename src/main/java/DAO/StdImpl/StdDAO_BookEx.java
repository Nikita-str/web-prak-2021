package DAO.StdImpl;

import DAO.Interfaces.I_BookExDAO;
import DAO.Interfaces.StdImpl_BookExDAO;
import models.BookExHistory;
import models.BookExamples;
import models.Readers;
import utils.SQL_FuncCall;
import utils.SessionHelper;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class StdDAO_BookEx extends StdImpl_BookExDAO implements I_BookExDAO {

    @Override
    public BookExamples GetBookExById(int bk_ex_id) {
        return SessionHelper.InSessionActWithR((ses -> {
            TypedQuery<BookExamples> q = ses.createQuery("FROM BookExamples WHERE book_ex_id = '"+bk_ex_id+"'", BookExamples.class);
            return q.getSingleResult();
        }));
    }

    @Override public void BookExDereg(int bk_ex_id, boolean need_to_ret)
    { SessionHelper.InSessionAct(ses -> SQL_FuncCall.BookExDereg(ses, bk_ex_id, need_to_ret)); }

    @Override public List<BookExHistory> GetExBookHistory(int bk_ex_id)
    { return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.GetExBookHistory(ses, bk_ex_id), BookExHistory.class); }

    @Override public boolean BookIsDereg(int bk_ex_id)
    { return SessionHelper.InSessionActWithR(ses-> SQL_FuncCall.BookIsDereg(ses, bk_ex_id)); }

    @Override public boolean BookAlreadyTaked(int bk_ex_id)
    { return SessionHelper.InSessionActWithR((ses -> SQL_FuncCall.BookAlreadyTaked(ses, bk_ex_id))); }

    @Override public boolean TheSameBook(int ex_id_1, int ex_id_2)
    {return SessionHelper.InSessionActWithR((ses -> SQL_FuncCall.TheSameBook(ses, ex_id_1, ex_id_2))); }

    @Override public boolean ReaderHasBookEx(int bk_ex_id, int _lib_card_id)
    { return SessionHelper.InSessionActWithR((ses -> SQL_FuncCall.ReaderHasBookEx(ses, bk_ex_id, _lib_card_id))); }
}
