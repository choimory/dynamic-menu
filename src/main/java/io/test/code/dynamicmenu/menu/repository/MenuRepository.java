package io.test.code.dynamicmenu.menu.repository;

import io.test.code.dynamicmenu.menu.entity.Menu;
import io.test.code.dynamicmenu.menu.repository.querydsl.QMenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, QMenuRepository {
    Optional<Menu> findByIdAndDeletedAtIsNull(Long id);
}
