package tibetyo.content_hub;

public enum OTTURL {
    Wavve("https://www.wavve.com/?utm_source=GA&utm_medium=SA&utm_campaign=WAVVE_GA_NU_PC&utm_content=0.%EB%B8%8C%EB%9E%9C%EB%93%9C%ED%82%A4%EC%9B%8C%EB%93%9C_%ED%99%95%EC%9E%A5%EA%B2%80%EC%83%89&utm_term=%EC%9B%A8%EC%9D%B4%EB%B8%8C&gad_source=1&gclid=CjwKCAiAudG5BhAREiwAWMlSjJe3Q_9LuAlj9pHi4DAdcyvw6DKcqXosZnCbCa1mzORpCbfi1xSAWhoCgqIQAvD_BwE");

    private final String url;

    OTTURL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
