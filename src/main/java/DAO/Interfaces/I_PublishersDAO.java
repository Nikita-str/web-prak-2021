package DAO.Interfaces;

import models.Publishers;

import java.sql.SQLException;
import java.util.List;

public interface I_PublishersDAO {
    public List<Publishers> FindPublisher(String publ_name, Boolean complete_match);
    public Integer GetPublisherId(String publ_name);
    public Publishers GetPublisher(String publ_name);
    public Publishers GetPublisherById(Integer id);
    public List<Publishers> GetAllPublishers();
}
