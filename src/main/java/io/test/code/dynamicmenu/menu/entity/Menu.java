package io.test.code.dynamicmenu.menu.entity;

import io.test.code.dynamicmenu.common.entity.CommonDateTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
@NoArgsConstructor
@Getter
@Slf4j
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
        this.description = toUpdate.description;

        //결론 필요한거 = 새로운 뎁스, 이전 부모, 새 부모, 이전 부모의 뎁스, 새 부모의 뎁스

        //부모 재정리
        // 1. 수정중인 메뉴가 지금보다 상위 뎁스로 이동시 내 모든 자식의 부모를 수정중인 메뉴의 이전 부모로 변경해야함
        // 2. 수정중인 메뉴가 지금보다 하위 뎁스로 이동시 내 모든 자식의 부모를 수정중인 메뉴의 이전 부모로 변경해야함
        // 부모가 null(최상위)일시 최상위로 변경, not null일시 해당 부모로 변경
        // 결론 = 내 하위 자식의 부모를 내 부모의 id로 바꿔놔야함
        Menu oldParent = this.parent;
        Menu newParent = toUpdate.parent;
        this.changeParent(oldParent, newParent);

        //뎁스 재계산
        // 1. 수정중인 메뉴의 뎁스 재계산
        // 2. 수정중인 메뉴는 새로운 부모 뎁스(계산이 끝난)에서 +1
        // 3. 자식들의 뎁스는 2에서 +1 = 왜냐면 자식들은 수정중인 메뉴 밑에 따라붙게 할것이기 때문에
        // 4. 모든 자식들까지 +1, +2, +3... 계속 내려가야함
        // 5. 새 부모가 없을시(최상위로 변경될시) 뎁스는 1

        //변경 메뉴 꼭대기 찾은다음에 전체 계산
        Integer oldParentDepth = oldParent == null
                ? 0
                : oldParent.depth;
        Integer newParentDepth = toUpdate.parent == null
                ? 0
                : toUpdate.parent.depth;

        this.changeDepth(oldParentDepth, newParent);
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
        this.banners.forEach(Banner::delete);
        this.children.forEach(Menu::delete);
    }

    private void changeParent(Menu oldParent, Menu newParent){
        if(!CollectionUtils.isEmpty(this.children) && newParent != null){
            this.children.forEach((child) -> this.changeChildrenParent(child, oldParent));
        }

        this.parent = newParent;
    }

    private void changeDepth(Integer oldParentDepth, Menu newParent){
        this.depth = newParent == null
                ? 1
                : newParent.depth + 1;

        if(newParent != null) {
            this.children.forEach((child) -> this.changeChildDepth(child, oldParentDepth));
            newParent.children.forEach((child) -> this.changeChildDepth(child, newParent.depth));
        }
    }

    private void changeChildrenParent(Menu child, Menu oldParent){
        child.parent = oldParent;
    }

    private void changeChildDepth(Menu child, int newDepth){
        child.depth = newDepth + 1;
    }
}
