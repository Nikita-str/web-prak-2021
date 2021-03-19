package DAO.StdImpl;

import DAO.Interfaces.I_AuthorsDAO;

public class StdDAO_Factory {
    private static I_AuthorsDAO DAO_Author = null;
    private static StdDAO_Factory instance = null;


    public static synchronized StdDAO_Factory getInstance() {
        if (instance == null) instance = new StdDAO_Factory();
        return instance;
    }

    public I_AuthorsDAO getAuthorDao(){
        if(DAO_Author == null) DAO_Author = new StdDAO_Authors();
        return DAO_Author;
    }

}
