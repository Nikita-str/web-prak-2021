package DAO.Interfaces;

import models.*;
import java.util.List;

public abstract class StdImpl_BookExDAO implements I_BookExDAO{
    @Override public List<BookExHistory> GetExBookHistory(BookExamples ex){return GetExBookHistory(ex.getBookExId());}
    @Override public boolean BookIsDereg(BookExamples ex){return BookIsDereg(ex.getBookExId());}
    @Override public boolean BookAlreadyTaked(BookExamples ex){return BookAlreadyTaked(ex.getBookExId());}
    @Override public boolean TheSameBook(BookExamples ex_1, BookExamples ex_2){return  TheSameBook(ex_1.getBookExId(), ex_2.getBookExId());}
    @Override public boolean ReaderHasBookEx(BookExamples ex, Readers reader){return ReaderHasBookEx(ex.getBookExId(), reader.getLibraryCardId());}
}
