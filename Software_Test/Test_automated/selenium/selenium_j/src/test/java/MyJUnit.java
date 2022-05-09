import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

public class MyJUnit {
    WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--headed");
        driver = new ChromeDriver(ops);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    public void getTitle() {
        driver.get("https://demoqa.com/");
        String title = driver.getTitle();
        System.out.println(title);
        Assert.assertEquals("ToolsQA", title);
    }

    @Test
    public void checkIfImageExists() throws InterruptedException {
        driver.get("https://demoqa.com/");
//        WebElement image1= driver.findElement(By.cssSelector("img"));
//        Assert.assertTrue(String.valueOf(image1.isDisplayed()),true);
        Thread.sleep(20000);
        WebElement image2 = driver.findElement(By.xpath("//img[@src='/images/Toolsqa.jpg']"));
        Assert.assertTrue(String.valueOf(image2.isDisplayed()), true);

    }

    @Test
    public void writeSomething() {
        driver.get("https://demoqa.com/text-box");
        WebElement txtUsername = driver.findElement(By.id("userName"));
        //WebElement txtUsername=driver.findElement(By.cssSelector("[type=text]"));
//        WebElement txtUsername=driver.findElement(By.cssSelector("[placeholder='Full Name']"));
//        WebElement txtUsername=driver.findElement(By.tagName("input"));
//        WebElement txtUsername = driver.findElement(By.cssSelector("input"));

//        List<WebElement> elements= driver.findElements(By.tagName("input"));
//        elements.get(0).sendKeys("Rahim");
//        elements.get(1).sendKeys("rahim@test.com");

        txtUsername.sendKeys("Rahim");
        driver.findElement(By.id("userEmail")).sendKeys("rahim@test.com");
        Actions actions=new Actions(driver);
        WebElement btnSubmit= driver.findElement(By.id("submit"));
        actions.moveToElement(btnSubmit).click().perform();
    }
    @Test
    public void clickButton(){
        driver.get("https://demoqa.com/buttons");
        List<WebElement> buttons= driver.findElements(By.tagName("button"));
        buttons.get(3).click();
        Actions actions=new Actions(driver);
        actions.doubleClick(buttons.get(1)).perform();
        actions.contextClick(buttons.get(2)).perform();

        String doubleClickMessage= driver.findElement(By.id("doubleClickMessage")).getText();
        String rightClickMessage= driver.findElement(By.id("rightClickMessage")).getText();
        String dynamicClickMessage= driver.findElement(By.id("dynamicClickMessage")).getText();

        Assert.assertTrue(doubleClickMessage.contains("You have done a double click"));
        Assert.assertTrue(rightClickMessage.contains("You have done a right click"));
        Assert.assertTrue(dynamicClickMessage.contains("You have done a dynamic click"));


    }

    @After
    public void closeDriver() {
        driver.close();
        //driver.quit();
    }

}
