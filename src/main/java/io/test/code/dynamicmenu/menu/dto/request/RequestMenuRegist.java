package io.test.code.dynamicmenu.menu.dto.request;

import io.test.code.dynamicmenu.menu.entity.Banner;
import io.test.code.dynamicmenu.menu.entity.Menu;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@RequiredArgsConstructor
@Getter
public class RequestMenuRegist {
    private final Long parentId;
    @NotEmpty
    @NotNull
    private final String title;
    @NotEmpty
    private final String link;
    private final String description;
    private final List<@Valid RequestBanner> banners;

    public Menu toEntity(int calculatedDepth){
        Menu menu = Menu.builder()
                .parent(parentId == null
                        ? null
                        : Menu.builder()
                        .id(parentId)
                        .build())
                .title(title)
                .link(link)
                .depth(calculatedDepth)
                .description(description)
                .build();

        return menu.regist(banners == null
                ? null
                : banners.stream()
                .map((banner) -> RequestBanner.toEntity(banner, menu))
                .collect(Collectors.toUnmodifiableList()));
    }

    @Builder
    @RequiredArgsConstructor
    @Getter
    public static class RequestBanner {
        @NotNull
        @NotEmpty
        private final String title;
        @NotNull
        @NotEmpty
        private final String link;
        private final String description;
        @NotNull
        @NotEmpty
        private final String imagePath;
        @NotNull
        private final Integer imageWidth;
        @NotNull
        private final Integer imageHeight;
        @NotNull
        private final Integer imageSize;

        public static Banner toEntity(RequestBanner banner, Menu menu){
            return banner == null
                    ? null
                    : Banner.builder()
                    .menu(menu)
                    .title(banner.getTitle())
                    .link(banner.getLink())
                    .description(banner.getDescription())
                    .imagePath(banner.imagePath)
                    .imageWidth(banner.imageWidth)
                    .imageHeight(banner.imageHeight)
                    .imageSize(banner.imageSize)
                    .build();
        }
    }
}
