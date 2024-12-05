package tibetyo.content_hub.content;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import tibetyo.content_hub.ContentCast;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private ContentCategory category;
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    private List<ContentCast> contentCasts;

    @Builder(access = AccessLevel.PUBLIC)
    public Content(String title, String description, ContentCategory category) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.contentCasts = new ArrayList<>();
    }

    public void addContentCast(ContentCast contentCast) {
        this.contentCasts.add(contentCast);
        contentCast.setContent(this);
    }
}
