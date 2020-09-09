package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.function.Supplier;

public class WebDriverProvider implements Supplier<WebDriver> {
    WebDriverConfig webDriverConfig;

    @Override
    public WebDriver get() {
        webDriverConfig = ConfigFactory.newInstance().create(WebDriverConfig.class);
        if (webDriverConfig.isRemote()) {
            switch (webDriverConfig.browserName()) {
                case CHROME:
                    return getRemoteChrome(webDriverConfig);
                case FIREFOX:
                    return getRemoteFirefox(webDriverConfig);
            }
        } else {
            switch (webDriverConfig.browserName()) {
                case CHROME:
                    WebDriverManager.chromedriver().setup();
                    return new ChromeDriver();
                case FIREFOX:
                    WebDriverManager.firefoxdriver().setup();
                    return new FirefoxDriver();
            }
        }
        throw new RuntimeException("Incorrect browser name");
    }

    private <T> T getBrowserOptions(String browserName) {
        if ("CHROME".equals(browserName)) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-notifications");
            chromeOptions.addArguments("--disable-infobars");
            chromeOptions.addArguments("--lang=ru");
            return (T) chromeOptions;
        } else {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--no-sandbox");
            firefoxOptions.addArguments("--disable-notifications");
            firefoxOptions.addArguments("--disable-infobars");
            firefoxOptions.addArguments("--lang=ru");
            return (T) firefoxOptions;
        }
    }
    
    private RemoteWebDriver getRemoteChrome(WebDriverConfig webDriverConfig) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", BrowserName.CHROME);
        capabilities.setCapability("browserVersion", webDriverConfig.chromeRemoteVersion());
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        capabilities.setCapability("videoFrameRate", 24);
        capabilities.setCapability(ChromeOptions.CAPABILITY, (ChromeOptions) getBrowserOptions("CHROME"));
        return new RemoteWebDriver(webDriverConfig.remoteUrl(), capabilities);
    }

    private RemoteWebDriver getRemoteFirefox(WebDriverConfig webDriverConfig) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", BrowserName.FIREFOX);
        capabilities.setCapability("browserVersion", webDriverConfig.firefoxRemoteVersion());
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        capabilities.setCapability("videoFrameRate", 24);
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, (FirefoxOptions) getBrowserOptions("FIREFOX"));
        return new RemoteWebDriver(webDriverConfig.remoteUrl(), capabilities);
    }


}
