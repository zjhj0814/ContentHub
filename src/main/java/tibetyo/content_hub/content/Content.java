package tibetyo.content_hub.content;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tibetyo.content_hub.ContentCast;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ContentCategory category;
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    private List<ContentCast> contentCasts;

    public Content(String title, String description, ContentCategory category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }
}
