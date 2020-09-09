package tests;

import common.WebDriverProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class OzonTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new WebDriverProvider().get();
        driver.manage().window().maximize();
    }

    @Test
    public void testMainPage() {
        driver.get("https://www.ozon.ru");
    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
