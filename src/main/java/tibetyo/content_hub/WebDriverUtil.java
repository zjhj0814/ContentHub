package tibetyo.content_hub;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static tibetyo.content_hub.WebDriverProperty.WEB_DRIVER_ID;
import static tibetyo.content_hub.WebDriverProperty.WEB_DRIVER_PATH;

public class WebDriverUtil {
    private WebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            System.setProperty(WEB_DRIVER_ID.getPath(), WEB_DRIVER_PATH.getPath());
            driver = new ChromeDriver();
        }
        return driver;
    }

    private void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}
