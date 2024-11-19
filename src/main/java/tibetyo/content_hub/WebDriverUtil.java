package tibetyo.content_hub;

import static tibetyo.content_hub.WebDriverProperty.*;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverUtil {
    private WebDriver driver;
    public WebDriverUtil(){
        process();
    }

    private void process(){
        System.setProperty(WEB_DRIVER_ID.getPath(), WEB_DRIVER_PATH.getPath());
        driver = new ChromeDriver();

        try{
            getDramaData();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        driver.close();
        driver.quit();
    }

    private void getDramaData() throws InterruptedException{
        driver.get("https://www.wavve.com/?utm_source=GA&utm_medium=SA&utm_campaign=WAVVE_GA_NU_PC&utm_content=0.%EB%B8%8C%EB%9E%9C%EB%93%9C%ED%82%A4%EC%9B%8C%EB%93%9C_%ED%99%95%EC%9E%A5%EA%B2%80%EC%83%89&utm_term=WAVVE&gad_source=1&gclid=CjwKCAiAudG5BhAREiwAWMlSjBgVd4byl9qxvSNmzzhXzEyejcgqLWSu8TJ647OcntP8RVRev9c6hBoCfvwQAvD_BwE");

        WebElement dramaSelectionLink = driver.findElement(By.xpath("//a[contains(text(), '드라마')]"));
        dramaSelectionLink.click();
        Thread.sleep(3000); //webdriverwait 사용으로 변경


    }

}
