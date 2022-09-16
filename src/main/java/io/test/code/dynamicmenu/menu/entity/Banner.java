package io.test.code.dynamicmenu.menu.entity;

import io.test.code.dynamicmenu.common.entity.CommonDateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Banner extends CommonDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
    private String title;
    private String link;
    private String description;
    private String imagePath;
    private Integer imageWidth;
    private Integer imageHeight;
    private Integer imageSize;

    @Builder(toBuilder = true)
    public Banner(LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt, Long id, Menu menu, String title, String link, String description, String imagePath, Integer imageWidth, Integer imageHeight, Integer imageSize) {
        super(createdAt, modifiedAt, deletedAt);
        this.id = id;
        this.menu = menu;
        this.title = title;
        this.link = link;
        this.description = description;
        this.imagePath = imagePath;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imageSize = imageSize;
    }
}
