package DAO.StdImpl;
import DAO.Interfaces.I_PublishersDAO;
import models.Publishers;
import utils.SQL_FuncCall;
import utils.SessionHelper;

import java.util.List;

public class StdDAO_Publishers implements I_PublishersDAO {

    @Override
    public List<Publishers> FindPublisher(String publ_name, Boolean complete_match) {
        return SessionHelper.InSessionActWithL(ses -> SQL_FuncCall.FindPublisher(ses, publ_name, complete_match), Publishers.class);
    }

    @Override
    public Integer GetPublisherId(String publ_name) {
        return SessionHelper.InSessionActWithR(ses -> SQL_FuncCall.GetPublisherId(ses, publ_name));
    }

    @Override
    public Publishers GetPublisher(String publ_name) {
        return SessionHelper.InSessionActWithR(ses -> ses.load(Publishers.class, GetPublisherId(publ_name)));
    }

    @Override
    public Publishers GetPublisherById(Integer id) {
        return SessionHelper.InSessionActWithR(ses -> ses.load(Publishers.class, id));
    }

    @Override
    public List<Publishers> GetAllPublishers() {
        return FindPublisher("", false);
    }
}
