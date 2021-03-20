package DAO.Interfaces;

import models.BookExHistory;
import models.BookExamples;
import models.Readers;

import java.util.List;

public abstract class StdImpl_ReaderDAO implements I_ReadersDAO{
    @Override public Boolean ReaderHasBookEx(BookExamples book_ex, Readers reader) { return ReaderHasBookEx(book_ex.getBookExId(), reader.getLibraryCardId()); }
    @Override public Boolean ReaderHasBookEx(Integer bk_ex_id, Readers reader){return  ReaderHasBookEx(bk_ex_id, reader.getLibraryCardId());}
    @Override public Boolean ReaderHasBookEx(BookExamples book_ex, Integer _lib_card_id){return  ReaderHasBookEx(book_ex.getBookExId(), _lib_card_id);}
    @Override public List<BookExHistory> GetReaderHistory(Readers reader){return GetReaderHistory(reader.getLibraryCardId());}
    @Override public List<BookExHistory> GetReaderCurBook(Readers reader){return GetReaderCurBook(reader.getLibraryCardId());}
    @Override public List<BookExHistory> GetReaderOverdueBook(Readers reader, Boolean only_cur){return GetReaderOverdueBook(reader.getLibraryCardId(), only_cur);}
    @Override public Boolean ReaderCanPassLibCard(Readers reader){return  ReaderCanPassLibCard(reader.getLibraryCardId());}
}
