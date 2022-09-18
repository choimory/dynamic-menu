package io.test.code.dynamicmenu.menu.entity;

import io.test.code.dynamicmenu.common.entity.CommonDateTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
@NoArgsConstructor
@Getter
public class Menu extends CommonDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer depth;
    private String title;
    private String link;
    private String description;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Menu parent;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> children = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Banner> banners = new ArrayList<>();

    @Builder(toBuilder = true)
    public Menu(LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt, Long id, Integer depth, String title, String link, String description, Menu parent, List<Menu> children, List<Banner> banners) {
        super(createdAt, modifiedAt, deletedAt);
        this.id = id;
        this.depth = depth;
        this.title = title;
        this.link = link;
        this.description = description;
        this.parent = parent;
        this.children = children;
        this.banners = banners;
    }

    public Menu regist(List<Banner> banners) {
        this.banners = banners;
        return this;
    }

    public void update(Menu toUpdate) {
        this.title = toUpdate.title;
        this.link = toUpdate.link;
        if(StringUtils.hasText(toUpdate.description)) this.description = toUpdate.description;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
        this.banners.forEach(Banner::delete);
        this.children.forEach(Menu::delete);
    }
}
