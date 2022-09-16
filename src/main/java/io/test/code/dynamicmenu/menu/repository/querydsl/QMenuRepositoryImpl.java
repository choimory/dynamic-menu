package io.test.code.dynamicmenu.menu.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import io.test.code.dynamicmenu.menu.entity.QMenu;
import io.test.code.dynamicmenu.menu.repository.querydsl.expression.QMenuExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static io.test.code.dynamicmenu.menu.entity.QMenu.menu;
import static io.test.code.dynamicmenu.menu.repository.querydsl.expression.QMenuExpression.containsTitle;
import static io.test.code.dynamicmenu.menu.repository.querydsl.expression.QMenuExpression.eqParentId;
import static io.test.code.dynamicmenu.menu.repository.querydsl.expression.QMenuExpression.eqTitleAndParentId;
import static io.test.code.dynamicmenu.menu.repository.querydsl.expression.QMenuExpression.gtLastId;

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
                .where(eqTitleAndParentId(title, parentId)
                        .and(menu.deletedAt.isNull()))
                .fetchFirst();

        return result != null;
    }

    @Override
    public List<MenuDto> findAllNoOffset(Long lastId, Long parentId, String title, int size) {
        return query.select(
                Projections.fields(
                        MenuDto.class,
                        menu.id,
                        menu.title,
                        menu.link,
                        menu.description))
                .from(menu)
                .where(gtLastId(lastId),
                        eqParentId(parentId),
                        containsTitle(title))
                .limit(size)
                .fetch();
    }
}
