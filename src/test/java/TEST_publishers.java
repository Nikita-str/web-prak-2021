import DAO.Interfaces.I_AuthorsDAO;
import DAO.Interfaces.I_PublishersDAO;
import DAO.StdImpl.StdDAO_Factory;
import models.Publishers;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import utils.DatabaseHelper;

public class TEST_publishers {

    @BeforeClass
    public void Before() throws Exception {
        if(!DatabaseHelper.DataBaseClear()) throw new Exception();
    }

    @Test
    public void TestPublishers() {
        StdDAO_Factory factory = StdDAO_Factory.getInstance();
        I_PublishersDAO pub_dao = factory.getPublisherDao();
        int len = pub_dao.FindPublisher(null, false).size();
        Publishers pub_1 =  pub_dao.GetPublisher("HEH");
        Publishers pub_eq =  pub_dao.GetPublisher("HEH");
        Assert.assertEquals(pub_eq.getPId(), pub_1.getPId());

        Publishers pub_2 =  pub_dao.GetPublisher("HEHEH");
        Publishers pub_3 =  pub_dao.GetPublisher("HEHEHEH");
        Publishers pub_3_eq =  pub_dao.GetPublisher("HEHEHEH");
        Publishers pub_4 =  pub_dao.GetPublisher("HEHEHEHEH");
        Publishers pub_5 =  pub_dao.GetPublisher("######");

        Assert.assertEquals(pub_dao.FindPublisher("######", true).get(0).getPId(), pub_5.getPId());
        
        Assert.assertEquals(pub_dao.GetPublisherById(pub_5.getPId()).getPName(), pub_5.getPName());

        Assert.assertEquals(pub_dao.GetAllPublishers().size(), 5);
        Assert.assertEquals(pub_dao.FindPublisher("HEH", false).size(), 4);
        Assert.assertEquals(pub_dao.FindPublisher("HEHEH", false).size(), 3);
        Assert.assertEquals(pub_dao.FindPublisher("HEH", true).size(), 1);
    }
}
