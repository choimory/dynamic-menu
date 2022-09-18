package io.test.code.dynamicmenu.menu.repository.querydsl.expression;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

import static io.test.code.dynamicmenu.menu.entity.QMenu.menu;

public class QMenuExpression {
    public static BooleanExpression eqTitleAndParentId(String title, Long parentId){
        return parentId == null
                ? menu.title.eq(title)
                .and(menu.parent.id.isNull())
                : menu.title.eq(title)
                .and(menu.parent.id.eq(parentId));
    }

    public static BooleanExpression eqDepth(Integer depth){
        return depth == null
                ? null
                : menu.depth.eq(depth);
    }

    public static BooleanExpression eqParentId(Long parentId){
        return parentId == null
                ? null
                : menu.parent.id.eq(parentId);
    }

    public static BooleanExpression containsTitle(String title){
        return !StringUtils.hasText(title)
                ? null
                : menu.title.contains(title);
    }

    public static BooleanExpression gtLastId(Long id){
        return id == null
                ? menu.id.gt(0)
                : menu.id.gt(id);
    }
}
