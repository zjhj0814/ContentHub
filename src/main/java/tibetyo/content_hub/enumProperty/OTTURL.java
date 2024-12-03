package tibetyo.content_hub.enumProperty;

public enum OTTURL {
    Wavve("https://www.wavve.com/member/login?referer=%2F");

    private final String url;

    OTTURL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
