import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    String MOBI_PAY_URL = "https://next.privat24.ua/mobile";


    @Test
    void rechargeMobile() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();

        // Объявляем елементы
        By phoneNumber = By.xpath("//input[@data-qa-node='phone-number']");
        By amount = By.xpath("//input[@data-qa-node='amount']");
        By numberdebitSource = By.xpath("//input[@data-qa-node='numberdebitSource']");
        By expiredebitSource = By.xpath("//input[@data-qa-node='expiredebitSource']");
        By cvvdebitSource = By.xpath("//input[@data-qa-node='cvvdebitSource']");
        By firstNamedebitSource = By.xpath("//input[@data-qa-node='firstNamedebitSource']");
        By lastNamedebitSource = By.xpath("//input[@data-qa-node='lastNamedebitSource']");
        By termsLink = By.xpath("//a[@href='https://privatbank.ua/terms']");
        By submit = By.xpath("//button[@data-qa-node='submit']");

        By expectedPhoneNumber = By.xpath("//div[@data-qa-node='details']");
        By expectedCardNumber = By.xpath("//td[@data-qa-node='card']");
        By expectedSum = By.xpath("//div[@data-qa-node='amount']");

        //Действия над элементами
        driver.get(MOBI_PAY_URL);
        driver.getTitle();
        driver.findElement(phoneNumber).sendKeys("507015958");
        driver.findElement(amount).sendKeys(Keys.COMMAND+"A");
        driver.findElement(amount).sendKeys(Keys.BACK_SPACE);
        driver.findElement(amount).sendKeys("205");
        driver.findElement(numberdebitSource).sendKeys("4552331448138217");
        driver.findElement(expiredebitSource).sendKeys("0524");
        driver.findElement(cvvdebitSource).sendKeys("926");
        driver.findElement(firstNamedebitSource).sendKeys("Taras");
        driver.findElement(lastNamedebitSource).sendKeys("Testenko");
        driver.findElement(termsLink).click();
        driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);

        // AssertionsForTitleAndPageUrlOfTermsLink
        Assertions.assertEquals("Умови та правила", driver.getTitle());
        Assertions.assertEquals("https://privatbank.ua/terms", driver.getCurrentUrl());

        driver.switchTo().window((String) driver.getWindowHandles().toArray()[0]);
        driver.findElement(submit).submit();

        //Описание проверок
        Assertions.assertEquals("Поповнення рахунку мобільного телефону банківською карткою онлайн — Приват24",
                driver.getTitle());
        Assertions.assertEquals("Поповнення телефону. На номер +380507015958",
                driver.findElement(expectedPhoneNumber).getText());
        Assertions.assertEquals("4552 **** **** 8217",
                driver.findElement(expectedCardNumber).getText());
        Assertions.assertEquals("205 UAH",
                driver.findElement(expectedSum).getText());



        driver.quit();
    }
}
