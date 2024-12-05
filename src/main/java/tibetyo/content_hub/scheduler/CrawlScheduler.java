package tibetyo.content_hub.scheduler;

import lombok.AllArgsConstructor;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tibetyo.content_hub.crawler.DramaCrawler;
import tibetyo.content_hub.service.ContentCastService;
import tibetyo.content_hub.util.WebDriverUtil;

import java.time.Duration;

@Component
@AllArgsConstructor
public class CrawlScheduler {
    private final WebDriverUtil webDriverUtil;
    private final ContentCastService contentCastService;

    @Scheduled(fixedRate = 604800000) //7일
    public void scheduleCrawling() throws InterruptedException {
        System.out.println("Crawling started");

        WebDriver webDriver = webDriverUtil.getDriver();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        try {
            DramaCrawler dramaCrawler = new DramaCrawler(webDriver, webDriverWait, jsExecutor, contentCastService);
            dramaCrawler.crawlContent();
        } catch (Exception e) {
            System.out.println("--------------------");
            System.out.println("Error occurred during crawling: " + e.getMessage());
            System.out.println("--------------------");

        } finally {
            webDriverUtil.quitDriver();

            System.out.println("Crawling finished");
        }
    }
}
