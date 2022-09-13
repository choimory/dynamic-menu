-- menu
insert into menu(id, parent_id, depth, title, link, description) values (1, null, 1, '루트-1', 'naver.com', '루트메뉴 1'), (2, 1, 2, '2층-1', 'kakao.com', '2층메뉴 1'), (3, 1, 2, '2층-2', 'daum.net', '2층메뉴 2'), (4, 2, 2, '3층-1', 'naver.com', '3층메뉴 1');

-- banner
insert into banner(id, menu_id, title, link, description, image_path) values (1, 1, '루트1 배너1', 'naver.com', '설명', 'img');