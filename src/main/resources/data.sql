insert into categories (id, name)
values (1, 'Burgers'),
       (2, 'Pizzas');

insert into brands (id, name, category_id)
values (1, 'Burgerking', 1),
       (2, 'McDonalds', 1);

insert into products (
    id,
    name,
    price,
    image_url,
    brand_id,
    discount_value,
    start_at,
    end_at
) values
    (1, '오리지널스 살사바르데 세트', 12500, 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-1-set.png', 1, null, null, null),
    (2, '오리지널스 이탈리안 살사바르데', 10500, 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-1.png', 1, 10, '2026-01-01', '2026-01-11'),
    (3, '오리지널스 뉴욕 스테이크 세트', 13900, 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-2-set.png', 1, null, null, null),
    (4, '오리지널스 뉴욕 스테이크', 11400, 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-2.png', 1, 15, '2026-01-01', '2026-01-11'),
    (5, '더오치 맥시멈 2', 11500, 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-3.png', 1, null, null, null),
    (6, '더오치 맥시멈 2 세트', 14000, 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-3-set.png', 1, null, null, null),
    (7, '더오치 맥시멈 3', 14900, 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-4.png', 1, 20, '2026-01-01', '2026-01-11'),
    (8, '더오치 맥시멈 3 세트', 17400, 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-4-set.png', 1, null, null, null),
    (9, '더오치 맥시멈 원파운더', 17900, 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-5.png', 1, null, null, null),
    (10, '더오치 맥시멈 원파운더 세트', 20400, 'https://nochigima.s3.ap-northeast-2.amazonaws.com/burgerking-5-set.png', 1, null, null, null);
