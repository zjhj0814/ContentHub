package tibetyo.content_hub;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cast {
    @Id
    @GeneratedValue
    @Column(name = "cast_id")
    private Long id;

    @Column(name = "cast_name")
    private String name;

    @OneToMany(mappedBy = "cast", cascade = CascadeType.ALL)
    public List<ContentCast> contentCasts;

    @Builder
    public Cast(String name) {
        this.name = name;
        this.contentCasts = new ArrayList<>();
    }

    public void addContentCast(ContentCast contentCast) {
        contentCasts.add(contentCast);
        contentCast.setCast(this);
    }
}
