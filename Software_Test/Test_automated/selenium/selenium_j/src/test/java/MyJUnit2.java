import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

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
        sleep(20000);
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
        actions.moveToElement(btnSubmit).click().build().perform();
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
    @Test
    public void alertHandle() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        driver.switchTo().alert().accept();
    }
    @Test
    public void alertHandlewithDelay() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("timerAlertButton")).click();
        sleep(7000);
        driver.switchTo().alert().accept();
    }
    @Test
    public void dialogBoxHandle(){
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("confirmButton")).click();
        driver.switchTo().alert().dismiss();
    }
    @Test
    public void promptHandle(){
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("promtButton")).click();
        driver.switchTo().alert().sendKeys("Salman");
        driver.switchTo().alert().accept();
    }
    @Test
    public void selectDate(){
        driver.get("https://demoqa.com/date-picker");
        driver.findElement(By.id("datePickerMonthYearInput")).click();
        driver.findElement(By.id("datePickerMonthYearInput")).clear();
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys("04/14/2022");
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.ENTER);

    }
    @Test
    public void selectDropdown(){
        driver.get("https://demoqa.com/select-menu");
        Select options=new Select(driver.findElement(By.id("oldSelectMenu")));
        options.selectByValue("3");
        //options.selectByIndex(2);
        options.selectByVisibleText("Green");

    }
    @Test
    public void selectMultipleDropdown(){
        driver.get("https://demoqa.com/select-menu");
        Select options=new Select(driver.findElement(By.id("cars")));
        if(options.isMultiple()){
            options.selectByValue("volvo");
            options.selectByValue("audi");
        }
    }
    @Test
    public void handleMultipleTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(3000);
        ArrayList<String> w = new ArrayList(driver.getWindowHandles());
        //switch to open tab
        driver.switchTo().window(w.get(1));
        System.out.println("New tab title: " + driver.getTitle());
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertEquals(text,"This is a sample page");
        driver.close();
        driver.switchTo().window(w.get(0));
    }
    @Test
    public void handleWindow(){
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id(("windowButton"))).click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();

        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String text= driver.findElement(By.id("sampleHeading")).getText();
                Assert.assertTrue(text.contains("This is a sample page"));
            }

        }

    }
    @Test
    public void modalDialog(){
        driver.get("https://demoqa.com/modal-dialogs");
        driver.findElement(By.id("showSmallModal")).click();
        String text= driver.findElement(By.className("modal-body")).getText();
        System.out.println(text);
        driver.findElement(By.id("closeSmallModal")).click();
    }
    @Test
    public void scrapData(){
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-table"));
        List<WebElement> allRows = table.findElements(By.className("rt-tr"));
        int i=0;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.className("rt-td"));
            for (WebElement cell : cells) {
                i++;
                System.out.println("num["+i+"] "+ cell.getText());

            }
        }
    }
    @Test
    public void uploadImage(){
       driver.get("https://demoqa.com/upload-download");
       driver.findElement(By.id("uploadFile")).sendKeys("D:\\me.jpg");
    }
    @Test
    public void handleIFrame(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame1");
        String text= driver.findElement(By.id("sampleHeading")).getText();
        System.out.println(text);
        driver.switchTo().defaultContent();

    }

    @After
    public void closeDriver() {
        driver.close();
        //driver.quit();
    }

}
