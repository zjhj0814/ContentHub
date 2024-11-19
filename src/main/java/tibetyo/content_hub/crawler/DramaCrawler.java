package tibetyo.content_hub.crawler;

import static tibetyo.content_hub.OTTURL.Wavve;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tibetyo.content_hub.content.ContentCategory;
import tibetyo.content_hub.dto.ContentCrawlDto;

@AllArgsConstructor
public class DramaCrawler implements ContentCrawler {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;
    private ContentCastService contentCastService;

    @Override
    public void crawlContent() throws InterruptedException {
        driver.get(Wavve.getUrl());
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        jsExecutor = (JavascriptExecutor) driver;

        //드라마>드라마 전체 페이지로 이동
        WebElement dramaSectionLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '드라마')]")));
        dramaSectionLink.click();

        WebElement genreDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".w-select")));
        genreDropdown.click();

        WebElement allDramaOption = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '드라마 전체')]")));
        allDramaOption.click();

        int scrollHeight = 0;
        Set<String> processedTitles = new HashSet<>();
        //드라마 목록 크롤링
        while (true) {
            List<WebElement> dramaList = driver.findElements(By.cssSelector(".thumb.portrait"));
            //드라마 상세 정보 크롤링
            for (WebElement drama : dramaList) {
                String title = drama.findElement(By.tagName("a")).getAttribute("aria-label");
                if (processedTitles.add(title)) {
                    WebElement dramaLink = drama.findElement(By.tagName("a"));
                    dramaLink.click();

                    List<WebElement> actorListElements = driver.findElements(By.cssSelector("span.content-actor-list"));
                    List<String> actorNames = actorListElements.stream()
                            .map(actorListElement -> actorListElement.findElement(By.tagName("a")))
                            .map(WebElement::getText)
                            .toList();

                    WebElement descriptionElement = driver.findElement(By.cssSelector("p.preview-dsc"));
                    String description = descriptionElement.getText();

                    contentCastService.saveCrawlingData(
                            new ContentCrawlDto(title, ContentCategory.DRAMA, actorNames, description)
                    );

                    driver.navigate().back();
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".thumb.portrait")));
                }
            }

            jsExecutor.executeScript("window.scrollTo(0,document.body.scrollHeight);");
            Thread.sleep(60);

            int newScrollHeight = Integer.parseInt(
                    jsExecutor.executeScript("return document.body.scrollHeight").toString());
            
            if (newScrollHeight == scrollHeight) {
                break;
            }
            scrollHeight = newScrollHeight;
        }
    }
}
