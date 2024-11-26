package tibetyo.content_hub.crawler;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tibetyo.content_hub.content.ContentCategory;
import tibetyo.content_hub.dto.ContentCrawlDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static tibetyo.content_hub.OTTURL.Wavve;

@AllArgsConstructor
public class DramaCrawler implements ContentCrawler {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;
    private ContentCastService contentCastService;

    @Override
    public void crawlContent() throws InterruptedException {
        driver.get(Wavve.getUrl());

        // 팝업 닫기
        closePopup();

        // 드라마 > 드라마 전체 페이지로 이동
        WebElement dramaSectionLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '드라마')]"))
        );
        dramaSectionLink.click();

        WebElement genreDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(".w-select"))
        );
        genreDropdown.click();

        WebElement allDramaOption = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '드라마 전체')]"))
        );
        allDramaOption.click();

        int scrollHeight = 0;
        Set<String> processedTitles = new HashSet<>();
        System.out.println("Starting...");

        // 드라마 목록 크롤링
        while (true) {
            List<WebElement> dramaList = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".thumb.portrait"))
            );

            System.out.println("dramaList size = " + dramaList.size());

            // 드라마 상세 정보 크롤링
            for (WebElement drama : dramaList) {
                try {
                    String title = wait.until(
                            ExpectedConditions.visibilityOf(drama.findElement(By.tagName("a")))
                    ).getAttribute("aria-label");

                    System.out.println("title = " + title);

                    if (processedTitles.add(title)) {
                        WebElement dramaLink = wait.until(
                                ExpectedConditions.elementToBeClickable(drama.findElement(By.tagName("a")))
                        );
                        dramaLink.click();

                        // 배우 목록 크롤링
                        List<WebElement> actorListElements = wait.until(
                                ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("span.content-actor-list"))
                        );

                        List<String> actorNames = actorListElements.stream()
                                .map(actorListElement -> wait.until(
                                        ExpectedConditions.visibilityOf(actorListElement.findElement(By.tagName("a")))
                                ))
                                .map(WebElement::getText)
                                .toList();

                        // 드라마 설명 크롤링
                        WebElement descriptionElement = wait.until(
                                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".preview-dsc"))
                        );
                        String description = descriptionElement.getText();

                        System.out.println("description = " + description);

                        // 배우와 드라마 정보를 저장
                        contentCastService.saveCrawlingData(
                                new ContentCrawlDto(title, ContentCategory.DRAMA, actorNames, description)
                        );

                        driver.navigate().back();

                        // 드라마 목록으로 돌아왔을 때 요소 대기
                        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".thumb.portrait")));
                    }
                } catch (Exception e) {
                    System.err.println("Error processing a drama: " + e.getMessage());
                    driver.navigate().back(); // 다음 크롤링을 위해 뒤로가기
                }
            }

            // 페이지 스크롤
            jsExecutor.executeScript("window.scrollTo(0,document.body.scrollHeight);");
            Thread.sleep(100);

            int newScrollHeight = Integer.parseInt(
                    jsExecutor.executeScript("return document.body.scrollHeight").toString()
            );

            if (newScrollHeight == scrollHeight) {
                break;
            }
            scrollHeight = newScrollHeight;
        }
    }

    private void closePopup() {
        try {
            WebElement popCloseButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector(".popup-button-close"))
            );
            if (popCloseButton.isDisplayed()) {
                popCloseButton.click();
                System.out.println("Popup closed.");
            }
        } catch (Exception e) {
            System.out.println("No popup found to close.");
        }
    }
}
