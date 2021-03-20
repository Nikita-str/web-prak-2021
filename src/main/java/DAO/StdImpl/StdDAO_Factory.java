package DAO.StdImpl;

import DAO.Interfaces.I_AuthorsDAO;
import DAO.Interfaces.I_PublishersDAO;
import DAO.Interfaces.I_ReadersDAO;

public class StdDAO_Factory {
    private static I_AuthorsDAO DAO_Author = null;
    private static I_PublishersDAO DAO_Publisher = null;
    private static I_ReadersDAO DAO_Reader = null;

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
}
