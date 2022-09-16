package io.test.code.dynamicmenu.menu.repository.querydsl.expression;

import com.querydsl.core.types.dsl.BooleanExpression;

import static io.test.code.dynamicmenu.menu.entity.QMenu.menu;

public class QMenuExpression {
    public static BooleanExpression eqTitleAndParentId(String title, Long parentId){
        return parentId == null
                ? menu.title.eq(title)
                .and(menu.parent.id.isNull())
                : menu.title.eq(title)
                .and(menu.parent.id.eq(parentId));
    }
}
