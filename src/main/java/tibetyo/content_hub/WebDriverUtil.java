package tibetyo.content_hub;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import static tibetyo.content_hub.enumProperty.WebDriverProperty.WEB_DRIVER_ID;
import static tibetyo.content_hub.enumProperty.WebDriverProperty.WEB_DRIVER_PATH;

@Component
public class WebDriverUtil {
    private WebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            System.setProperty(WEB_DRIVER_ID.getPath(), WEB_DRIVER_PATH.getPath());

            ChromeOptions options = new ChromeOptions();
            //options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-extensions");

            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}
