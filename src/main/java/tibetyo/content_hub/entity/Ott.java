package tibetyo.content_hub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Ott {
    @Id
    @GeneratedValue
    @Column(name = "ott_id")
    private Long id;
    @Column(name = "ott_name")
    private String name;
    //ott_image
}
