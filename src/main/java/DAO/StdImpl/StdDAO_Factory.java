package DAO.StdImpl;

import DAO.Interfaces.*;

public class StdDAO_Factory {
    private static I_AuthorsDAO DAO_Author = null;
    private static I_PublishersDAO DAO_Publisher = null;
    private static I_ReadersDAO DAO_Reader = null;
    private static I_BooksDAO DAO_Book= null;
    private static I_BookExDAO DAO_BookExs= null;
    private static I_BookExHistoryDAO DAO_BEH= null;

    private static StdDAO_Factory instance = null;

    public static synchronized StdDAO_Factory getInstance() {
        if (instance == null) instance = new StdDAO_Factory();
        return instance;
    }

    public I_AuthorsDAO getAuthorDao(){
        if(DAO_Author == null) DAO_Author = new StdDAO_Authors();
        return DAO_Author;
    }

    public I_PublishersDAO getPublisherDao(){
        if(DAO_Publisher == null) DAO_Publisher = new StdDAO_Publishers();
        return DAO_Publisher;
    }

    public I_ReadersDAO getReaderDao(){
        if(DAO_Reader == null) DAO_Reader = new StdDAO_Readers();
        return DAO_Reader;
    }

    public I_BooksDAO getBookDao(){
        if(DAO_Book == null) DAO_Book = new StdDAO_Books();
        return DAO_Book;
    }

    public I_BookExDAO getBookExDao(){
        if(DAO_BookExs == null) DAO_BookExs = new StdDAO_BookEx();
        return DAO_BookExs;
    }

    public I_BookExHistoryDAO getBookExHistoryDao(){
        if(DAO_BEH == null) DAO_BEH = new StdDAO_BookExHistory();
        return DAO_BEH;
    }
}
