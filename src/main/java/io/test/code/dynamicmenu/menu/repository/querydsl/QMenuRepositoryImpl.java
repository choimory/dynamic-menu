package io.test.code.dynamicmenu.menu.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import io.test.code.dynamicmenu.menu.entity.QMenu;
import io.test.code.dynamicmenu.menu.repository.querydsl.expression.QMenuExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static io.test.code.dynamicmenu.menu.entity.QMenu.menu;
import static io.test.code.dynamicmenu.menu.repository.querydsl.expression.QMenuExpression.eqTitleAndParentId;

@Repository
@RequiredArgsConstructor
public class QMenuRepositoryImpl implements QMenuRepository{
    private final JPAQueryFactory query;

    @Override
    public Optional<Integer> findDepthById(Long id) {
        return Optional.ofNullable(
                query.select(Projections.constructor(Integer.class, menu.depth))
                        .from(menu)
                        .where(menu.id.eq(id))
                        .fetchOne());
    }

    @Override
    public Boolean existByTitleAmongChild(String title, Long parentId) {
        Integer result = query.selectOne()
                .from(menu)
                .where(eqTitleAndParentId(title, parentId))
                .fetchFirst();

        return result != null;
    }
}
