import DAO.Interfaces.I_AuthorsDAO;
import DAO.StdImpl.StdDAO_Factory;
import models.Authors;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import utils.DatabaseHelper;
import utils.HibernateSessionFactoryUtil;

import java.sql.SQLException;

public class TEST_Authors{


    @BeforeClass
    public void Before() throws Exception {
        if(!DatabaseHelper.DataBaseClear()) throw new Exception();
    }

    @Test
    public void TestAuthor() {
        //AddAuthorToBook see in books test.

        StdDAO_Factory factory = StdDAO_Factory.getInstance();
        I_AuthorsDAO a_dao = factory.getAuthorDao();
        int len = a_dao.GetAllAuthor().size();

        Authors auth = a_dao.GetAuthor("Самуил", "Маршак", "Яковливич");
        Authors auth_eq = a_dao.GetAuthor("СамуИл", "Маршак", "яковливич");
        Assert.assertEquals(auth.getAuthorId(), auth_eq.getAuthorId());

        Authors auth_must_be_null = a_dao.GetAuthor(null, "author", null);
        Assert.assertEquals(auth_must_be_null, null);

        Authors auth_2 = a_dao.GetAuthor("not_real", "author", null);
        Authors auth_3 = a_dao.GetAuthor("not", "author", null);
        Authors auth_4 = a_dao.GetAuthor("Aura", "noThing", null);

        Assert.assertEquals(a_dao.GetAuthorById(auth_4.getAuthorId()).getAuthorId(), auth_4.getAuthorId());
        Assert.assertEquals(a_dao.GetAuthorId("aura", "nothiNG", null), auth_4.getAuthorId());

        Assert.assertEquals(a_dao.GetAuthorId("Самуил", "Маршак", "Яковливич"), auth.getAuthorId());

        Assert.assertEquals(a_dao.FindAuthor("сам", "мар", "яко", false).size(), 1);
        Assert.assertEquals(a_dao.FindAuthor("сам", "мар", "яко", true).size(), 0);
        Assert.assertEquals(a_dao.FindAuthor("самуил", "маршак", "яковливич", true).size(), 1);
        Assert.assertEquals(a_dao.FindAuthor("not", "aut", false).size(), 3);
        Assert.assertEquals(a_dao.FindAuthor("author", true).size(), 2);
        Assert.assertEquals(a_dao.FindAuthor("aura", null, true).size(), 1);
        Assert.assertEquals(a_dao.FindAuthor("a", true).size(), 0);

        Assert.assertEquals(a_dao.GetAllAuthor().size(), len + 4);
    }
}

