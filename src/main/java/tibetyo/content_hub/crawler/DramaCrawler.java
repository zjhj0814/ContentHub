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
        readyToCrawl();

        // 드라마 제목 수집
        Set<String> dramaTitles = collectDramaTitles();

        for (String title : dramaTitles) {
            try {
                // 드라마 상세 정보 페이지로 이동
                WebElement dramaLink = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath("//a[@aria-label='" + title + "']")
                        )
                );
                dramaLink.click();

                // 상세 페이지에서 배우 목록 크롤링
                List<String> actorNames = getActorNames();

                // 상세 페이지에서 설명 크롤링
                String description = getDescription();

                // 데이터 저장
                contentCastService.saveCrawlingData(
                        new ContentCrawlDto(title, ContentCategory.DRAMA, actorNames, description)
                );

                // 목록 페이지로 돌아가기
                driver.navigate().back();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@aria-label, '" + title + "')]")));

            } catch (Exception e) {
                System.err.println("Error processing drama: " + title + " - " + e.getMessage());
                driver.navigate().back(); // 에러 시 뒤로가기 시도
            }
        }
    }

    private String getDescription() {
        WebElement descriptionElement = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".content-clamp span span"))
        );
        String description = descriptionElement.getAttribute("aria-label");
        return description;
    }

    private List<String> getActorNames() {
        List<WebElement> actorNameElements = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".content-actor-box span a"))
        );
        List<String> actorNames = actorNameElements.stream()
                .map(actorNameElement -> actorNameElement.getAttribute("innerText").strip())
                .toList();
        return actorNames;
    }

    private void readyToCrawl() {
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
    }

    private Set<String> collectDramaTitles() throws InterruptedException {
        Set<String> dramaTitles = new HashSet<>();

        List<WebElement> dramaList = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".thumb.portrait a"))
        );
        dramaTitles.addAll(dramaList.stream()
                .map(drama -> drama.getAttribute("aria-label"))
                .toList());

        return dramaTitles;
    }


    private void closePopup() {
        try {
            WebElement popCloseButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector(".popup-button-close"))
            );
            if (popCloseButton.isDisplayed()) {
                popCloseButton.click();
            }
        } catch (Exception e) {
        }
    }
}
