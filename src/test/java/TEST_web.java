import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.*;
import junit.framework.*;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.internal.collections.Pair;
import utils.DatabaseHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TEST_web {
    protected final String appURL = "http://localhost:8080/jWeb/";
    protected WebDriver driver;

    private WebElement LeftBlock(){ return driver.findElement(By.className("left-block")); }

    private WebElement GetParent(WebElement elem){
        return (WebElement)((JavascriptExecutor)driver).executeScript("return arguments[0].parentNode;", elem);
    }

    private WebElement GetParent(WebElement elem, int level){
        for(int i = 0; i < level; i++)elem = GetParent(elem);
        return elem;
    }

    @BeforeClass
    public void setUp() {
        final String chromeDriverPath = "C:\\OtherPrograms\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1000, 1000));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private void IndexPage(){
        if(driver.findElements(By.xpath("//a[@href='index']")).size() > 0){
            driver.findElement(By.xpath("//a[@href='index']")).findElement(By.tagName("button")).click();
        }else {
            driver.get(appURL + "index");
        }
    }

    private Pair<String, String> GetUrl(){
        String str = driver.getCurrentUrl();
        str = str.substring(appURL.length());
        int ind_q = str.indexOf('?');
        String url = (ind_q < 0) ? str : str.substring(0, ind_q - 1);
        String param = (ind_q < 0) ? "" : str.substring(ind_q+1);
        return new Pair<>(url, param);
    }

    private int CreateUser(String name, String surname){
        if(driver.findElements(By.xpath("//a[@href='readers']")).size() == 0){
            driver.findElement(By.xpath("//a[@href='index']")).findElement(By.tagName("button")).click();
        }
        driver.findElement(By.xpath("//a[@href='readers']")).findElement(By.tagName("button")).click();
        driver.findElement(By.xpath("//button[text()='добавить читателя']")).click();
        driver.findElement(By.name("add_name")).sendKeys(name);
        driver.findElement(By.name("add_snake")).sendKeys(surname);
        driver.findElement(By.name("b_r_add")).click();
        return Integer.parseInt(GetUrl().second().substring(3));
    }

    private int CreateBook(String title, String pub, int amount){
        if(driver.findElements(By.xpath("//a[@href='books']")).size() == 0){
            driver.findElement(By.xpath("//a[@href='index']")).findElement(By.tagName("button")).click();
        }
        driver.findElement(By.xpath("//a[@href='books']")).findElement(By.tagName("button")).click();
        driver.findElement(By.xpath("//button[text()='добавить книгу']")).click();
        driver.findElement(By.name("add_title")).sendKeys(title);
        if(pub != null)driver.findElement(By.name("add_pub")).sendKeys(pub);
        if(amount > 0)driver.findElement(By.name("amount")).sendKeys(String.valueOf(amount));
        driver.findElement(By.name("b_b_add")).click();
        return Integer.parseInt(GetUrl().second().substring(3));
    }

    private void SearchById(int id){
        if(driver.findElements(By.xpath("//a[@href='readers']")).size() == 0){
            driver.findElement(By.xpath("//a[@href='index']")).findElement(By.tagName("button")).click();
        }
        driver.findElement(By.xpath("//a[@href='readers']")).findElement(By.tagName("button")).click();
        driver.findElement(By.xpath("//button[text()='найти читателя']")).click();
        driver.findElement(By.name("find_by_id")).sendKeys(""+id);
        GetParent(driver.findElement(By.name("find_by_id")), 3).findElement(By.className("ok")).click();
        driver.findElement(By.tagName("table")).findElement(By.tagName("a")).click();
    }

    @Test
    public void BigTest(){
        IndexPage();
        Assert.assertEquals(driver.getTitle(), "библиотека");
        int id_0 = CreateUser("First", "Reader");
        int id_1 = CreateUser("Second", "Surname");
        LeftBlock().findElement(By.tagName("button")).click();
        int id_2 = CreateUser("last", "Reader");
        Assert.assertNotEquals(id_0, id_1);
        Assert.assertNotEquals(id_0, id_2);
        Assert.assertNotEquals(id_1, id_2);
        int b_id_0 = CreateBook("T1", null, 2);
        int b_id_1 = CreateBook("T2", "HH", 0);
        int b_id_2 = CreateBook("Z", "HH", 3);
        int b_id_3 = CreateBook("G", "H", 0);

        //now in book:id=b_id_3 // 0 exs
        WebElement amount_inp = driver.findElement(By.name("amount"));
        amount_inp.sendKeys(String.valueOf(4));
        WebElement form_add = (WebElement)((JavascriptExecutor)driver).executeScript("return arguments[0].parentNode;", amount_inp);
        form_add.findElement(By.tagName("button")).click();
        driver.findElement(By.xpath("//button[text()='к экземплярам']")).click();
        List<WebElement> lis_01 = driver.findElements(By.tagName("li"));
        Assert.assertEquals(lis_01.size(), 4); // check that now 4 exs
        lis_01.get(2).findElement(By.tagName("a")).click();
        driver.findElement(By.xpath("//button[text()='взять книгу']")).click();
        int ex_id_cur = Integer.parseInt(GetUrl().second().substring(6));
        driver.findElement(By.xpath("//button[text()='найти читателя']")).click();
        driver.findElement(By.name("snake")).sendKeys("Reader");
        driver.findElement(By.className("ok")).click();
        int search_sz_0 = driver.findElements(By.tagName("a")).size();
        driver.findElement(By.xpath("//button[text()='к выбору книги']")).click();

        driver.findElement(By.xpath("//button[text()='найти читателя']")).click();
        driver.findElement(By.name("snake")).sendKeys("Reader");
        driver.findElement(By.name("find_name")).sendKeys("First");
        driver.findElement(By.className("ok")).click();
        int search_sz_1 = driver.findElements(By.tagName("a")).size();
        Assert.assertTrue(search_sz_0 > search_sz_1);

        driver.findElement(By.xpath("//a[@href='book_take?ex_id=" + ex_id_cur +"&r_id="+id_0+"']")).click();
        driver.findElement(By.name("delta_day")).sendKeys("15");
        driver.findElement(By.xpath("//button[text()='выдать']")).click();
        Assert.assertEquals(GetUrl().first(), "success");

        SearchById(id_0);
        Assert.assertEquals(driver.findElement(By.tagName("nav")).findElements(By.tagName("a")).size(), 1);
        driver.findElement(By.tagName("nav")).findElement(By.tagName("a")).click();
        Assert.assertEquals(driver.findElements(By.tagName("nav")).size(), 0);
        IndexPage();
    }


    @Test
    public void TakeFromReader(){
        IndexPage();
        int b_id_0 = CreateBook("Take From Reader", null, 2);
        int id_0 = CreateUser("First", "A");
        driver.findElement(By.xpath("//button[text()='взять книгу']")).click();
        driver.findElement(By.xpath("//button[text()='найти книгу']")).click();
        driver.findElement(By.name("find_title")).sendKeys("Take From Reader");
        driver.findElement(By.className("ok")).click();
        driver.findElement(By.tagName("tbody")).findElement(By.tagName("a")).click();
        driver.findElement(By.name("delta_day")).sendKeys("15");
        System.out.println(driver.getCurrentUrl());
        driver.findElement(By.xpath("//button[text()='выдать']")).click();
        IndexPage();
        SearchById(id_0);
        driver.findElement(By.xpath("//button[text()='взять книгу']")).click();
        driver.findElement(By.xpath("//button[text()='найти книгу']")).click();
        driver.findElement(By.name("find_title")).sendKeys("Take From Reader");
        driver.findElement(By.className("ok")).click();
        driver.findElement(By.tagName("tbody")).findElement(By.tagName("a")).click();
        System.out.println(driver.getCurrentUrl());
        Assert.assertEquals(driver.findElements(By.xpath("//button[text()='выдать']")).size(), 0); //already has that book
        IndexPage();
    }

    @Test
    public void Errors() {
        driver.get(appURL + "reader?id=999");
        Assert.assertNotEquals(driver.getTitle(), "библиотека");
        driver.get(appURL + "book_ex?ex_id=999");
        Assert.assertNotEquals(driver.getTitle(), "библиотека");
        driver.get(appURL + "book?id=999");
        Assert.assertNotEquals(driver.getTitle(), "библиотека");
    }

    @Test
    public void RetBookFromEx(){
        IndexPage();
        int id_0 = CreateUser("NN", "SS");
        int b_id_0 = CreateBook("RetIt", null, 9);
        driver.findElement(By.xpath("//button[text()='взять книгу']")).click();
        int ex_id = Integer.parseInt(GetUrl().second().substring(6));
        driver.findElement(By.xpath("//button[text()='найти читателя']")).click();
        driver.findElement(By.name("find_by_id")).sendKeys(""+id_0);
        driver.findElement(By.className("ok")).click();
        driver.findElement(By.tagName("tbody")).findElement(By.tagName("a")).click();
        driver.findElement(By.name("delta_day")).sendKeys("15");
        driver.findElement(By.xpath("//button[text()='выдать']")).click();
        IndexPage();
        driver.get(appURL + "book_ex?ex_id="+ex_id);
        driver.findElement(By.xpath("//button[text()='сдать книгу']")).click();
        Assert.assertEquals(GetUrl().second(), "id="+b_id_0);
        IndexPage();
    }

    @Test
    public void LostBookTest(){
        IndexPage();
        int id_0 = CreateUser("Lost", "Book");
        int b_id_0 = CreateBook("LostIt", null, 9);
        driver.findElement(By.xpath("//button[text()='взять книгу']")).click();
        int ex_id = Integer.parseInt(GetUrl().second().substring(6));
        driver.findElement(By.xpath("//button[text()='найти читателя']")).click();
        driver.findElement(By.name("find_by_id")).sendKeys(""+id_0);
        driver.findElement(By.className("ok")).click();
        driver.findElement(By.tagName("tbody")).findElement(By.tagName("a")).click();
        driver.findElement(By.name("delta_day")).sendKeys("15");
        driver.findElement(By.xpath("//button[text()='выдать']")).click();
        IndexPage();
        driver.get(appURL + "book_ex?ex_id="+ex_id);
        driver.findElement(By.xpath("//button[text()='утерена']")).click();
        Assert.assertEquals(GetUrl().second(), "id="+b_id_0);
        IndexPage();
    }
    @AfterClass
    public void clear() {
        driver.quit();
    }
}
