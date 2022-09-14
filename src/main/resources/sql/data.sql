-- menu
insert into menu(id, parent_id, depth, title, link, description, created_at, modified_at, deleted_at) values (1, null, 1, '루트-1', 'naver.com', '루트메뉴 1', '2022-01-01T00:00:00', '2022-01-01T00:00:00', null), (2, 1, 2, '2층-1', 'kakao.com', '2층메뉴 1', '2022-01-01T00:00:00', '2022-01-01T00:00:00', null), (3, 1, 2, '2층-2', 'daum.net', '2층메뉴 2', '2022-01-01T00:00:00', '2022-01-01T00:00:00', null), (4, 2, 2, '3층-1', 'naver.com', '3층메뉴 1', '2022-01-01T00:00:00', '2022-01-01T00:00:00', null);

-- banner
insert into banner(id, menu_id, title, link, description, image_path, image_width, image_height, image_size, created_at, modified_at, deleted_at) values (1, 1, '루트1 배너1', 'naver.com', '설명', '/foo/bar/abc.jpg', 500, 35, 123928, '2022-01-01T00:00:00', '2022-01-01T00:00:00', null);