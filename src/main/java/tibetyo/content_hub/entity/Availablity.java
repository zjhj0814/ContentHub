package tibetyo.content_hub.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Availablity {
    @Id
    @GeneratedValue
    @Column(name = "availablity_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ott_id")
    private Ott ott;

    @Enumerated(EnumType.STRING)
    private ContentStatus contentStatus;

    @Builder
    public Availablity(Long id, Content content, Ott ott, ContentStatus contentStatus) {
        this.id = id;
        this.content = content;
        this.ott = ott;
        this.contentStatus = contentStatus;
    }
}
