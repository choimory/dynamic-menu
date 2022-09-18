package io.test.code.dynamicmenu.menu.dto.dto;

import io.test.code.dynamicmenu.menu.entity.Banner;
import io.test.code.dynamicmenu.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MenuDto {
    private Long id;
    private Integer depth;
    private String title;
    private String link;
    private String description;
    private ParentMenuDto parent;
    private List<ChildMenuDto> children;
    private List<BannerDto> banners;

    public static MenuDto toDto(Menu menu){
        return menu == null
                ? null
                : MenuDto.builder()
                .id(menu.getId())
                .depth(menu.getDepth())
                .title(menu.getTitle())
                .link(menu.getLink())
                .description(menu.getDescription())
                .parent(ParentMenuDto.toDto(menu.getParent()))
                .children(menu.getChildren() == null
                        ? null
                        : menu.getChildren()
                        .stream()
                        .map(ChildMenuDto::toDto)
                        .collect(Collectors.toUnmodifiableList()))
                .banners(menu.getBanners() == null
                        ? null
                        : menu.getBanners()
                        .stream()
                        .map(BannerDto::toDto)
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ParentMenuDto{
        private Long id;
        private Integer depth;
        private String title;
        private String link;
        private String description;
        private ParentMenuDto parent;
        private List<BannerDto> banners;

        public static ParentMenuDto toDto(Menu menu){
            return menu == null
                    ? null
                    : ParentMenuDto.builder()
                    .id(menu.getId())
                    .depth(menu.getDepth())
                    .title(menu.getTitle())
                    .link(menu.getLink())
                    .description(menu.getDescription())
                    .parent(ParentMenuDto.toDto(menu.getParent()))
                    .banners(menu.getBanners() == null
                            ? null
                            : menu.getBanners()
                            .stream()
                            .map(BannerDto::toDto)
                            .collect(Collectors.toUnmodifiableList()))
                    .build();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class ChildMenuDto{
        private Long id;
        private Integer depth;
        private String title;
        private String link;
        private String description;
        private List<ChildMenuDto> children;
        private List<BannerDto> banners;

        public static ChildMenuDto toDto(Menu menu){
            return menu == null
                    ? null
                    : ChildMenuDto.builder()
                    .id(menu.getId())
                    .depth(menu.getDepth())
                    .title(menu.getTitle())
                    .link(menu.getLink())
                    .description(menu.getDescription())
                    .children(menu.getChildren() == null
                            ? null
                            : menu.getChildren()
                            .stream()
                            .map(ChildMenuDto::toDto)
                            .collect(Collectors.toUnmodifiableList()))
                    .banners(menu.getBanners() == null
                            ? null
                            : menu.getBanners()
                            .stream()
                            .map(BannerDto::toDto)
                            .collect(Collectors.toUnmodifiableList()))
                    .build();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class BannerDto{
        private Long id;
        private String title;
        private String link;
        private String description;
        private String imagePath;
        private Integer imageWidth;
        private Integer imageHeight;
        private Integer imageSize;

        public static BannerDto toDto(Banner banner){
            return banner == null
                    ? null
                    : BannerDto.builder()
                    .id(banner.getId())
                    .title(banner.getTitle())
                    .link(banner.getLink())
                    .description(banner.getDescription())
                    .imagePath(banner.getImagePath())
                    .imageWidth(banner.getImageWidth())
                    .imageHeight(banner.getImageHeight())
                    .imageSize(banner.getImageSize())
                    .build();
        }
    }
}
