package tibetyo.content_hub;

import java.util.List;

public interface ContentCrawler {
    List<Content> crawlContent() throws InterruptedException;
}
