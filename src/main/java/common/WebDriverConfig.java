package common;

import org.aeonbits.owner.Config;

import java.net.URL;

@Config.Sources("classpath:webdriver.properties")

public interface WebDriverConfig extends Config {

    @Key("webdriver.remote")
    boolean isRemote();

    @Key("webdriver.remote.url")
    URL remoteUrl();

    @Key("webdriver.browser.name")
    BrowserName browserName();

    @Key("webdriver.remote.password")
    String password();

    @Key("webdriver.remote.chrome.version")
    String chromeRemoteVersion();

    @Key("webdriver.remote.firefox.version")
    String firefoxRemoteVersion();
}
