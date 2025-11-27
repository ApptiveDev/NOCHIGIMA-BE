INSERT INTO category (id, name)
VALUES (1, '햄버거');

INSERT INTO brand (id, category_id, name, logo_image_url)
VALUES (1, 1, '버거킹', 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking.svg'),
       (2, 1, '맥도날드', 'https://nochigima.s3.ap-northeast-2.amazonaws.com/mcdonald.svg');

INSERT INTO promotion (brand_id, category_id, title, main_image_url, list_image_url, description, start_date, end_date)
VALUES
    -- 1. 맥도날드 (Brand ID: 2) - 종료일: 2025-12-01
    (2, 1, '크리스마스 트러플 치즈버거 + 탄산음료 M 18% 할인',
     NULL, NULL,
     '크리스마스 트러플 치즈버거 + 탄산음료 M 18% 할인',
     '2025-11-20', '2025-12-01'),

    (2, 1, '빅맥 세트 + 치즈스틱 2조각 22% 할인',
     NULL, NULL,
     '빅맥 세트 + 치즈스틱 2조각 22% 할인',
     '2025-11-20', '2025-12-01'),

    (2, 1, '불고기 버거 47% 할인',
     NULL, NULL,
     '불고기 버거 47% 할인',
     '2025-11-20', '2025-12-01'),

    (2, 1, '치즈스틱 2조각 50% 할인',
     NULL, NULL,
     '치즈스틱 2조각 50% 할인',
     '2025-11-20', '2025-12-01'),

    (2, 1, '맥크리스피 디럭스 버거 31% 할인',
     NULL, NULL,
     '맥크리스피 디럭스 버거 31% 할인',
     '2025-11-20', '2025-12-01'),

    (2, 1, '크리스마스 트러플 치즈버거 세트 9% 할인',
     NULL, NULL,
     '크리스마스 트러플 치즈버거 세트 9% 할인',
     '2025-11-20', '2025-12-01'),

    (2, 1, '더블 불고기 버거 세트 8% 할인',
     NULL, NULL,
     '더블 불고기 버거 세트 8% 할인',
     '2025-11-20', '2025-12-01'),

    (2, 1, '크리스마스 핫 트러플 치즈버거 세트 + 맥너겟 4조각 20% 할인',
     NULL, NULL,
     '크리스마스 핫 트러플 치즈버거 세트 + 맥너겟 4조각 20% 할인',
     '2025-11-20', '2025-12-01'),

    (2, 1, '치킨 텐더 2조각 30% 할인',
     NULL, NULL,
     '치킨 텐더 2조각 30% 할인',
     '2025-11-20', '2025-12-01'),

    -- [버거킹] 종료일: 2025-11-30
    (1, 1, '오리지널스 이탈리안 살사베르데 세트 8% 할인',
     'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-main.png', 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-list.jpg',
     '오리지널스 이탈리안 살사베르데 세트 8% 할인',
     '2025-11-20', '2025-11-30'),

    (1, 1, '베이비 버거 세트 10% 할인',
     'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-main.png', 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-list.jpg',
     '베이비 버거 세트 10% 할인',
     '2025-11-20', '2025-11-30'),

    (1, 1, '크리스퍼 양념 치킨 세트 11% 할인',
     'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-main.png', 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-list.jpg',
     '크리스퍼 양념 치킨 세트 11% 할인',
     '2025-11-20', '2025-11-30'),

    (1, 1, '골든 에그 트러플 머쉬룸 세트 7% 할인',
     'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-main.png', 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-list.jpg',
     '골든 에그 트러플 머쉬룸 세트 7% 할인',
     '2025-11-20', '2025-11-30');
