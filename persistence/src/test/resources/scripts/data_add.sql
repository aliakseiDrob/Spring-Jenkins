INSERT INTO gift_certificate (name, description, price, duration, is_available, create_date, last_update_date)
values ('first', 'for men', 128.01, 11, 1, '2021-03-21 20:11:10', '2021-03-24 20:11:10'),
       ('second', 'children', 250.20, 7, 1, '2021-03-06 20:11:10', '2021-03-11 20:11:10'),
       ('third', 'everybody', 48.50, 3, 1, '2021-03-26 19:11:10', '2021-03-28 20:11:10'),
       ('first', 'children', 48.50, 3, 1, '2021-03-20 19:11:10', '2021-03-28 20:11:10');

INSERT INTO tag (name)
VALUES ('first'),
       ('second');

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (4, 1);

INSERT INTO users (name, password)
VALUES ('Ivan', 'ivan'),
       ('Petr', 'petr');
INSERT INTO ROLES (role)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');
insert INTO users_role
values (1, 1),
       (2, 2);

INSERT INTO `orders` (date, order_cost, user_id, certificate_id)
VALUES ('2021-05-24 20:11:10', 340, 1, 1),
       ('2021-05-30 20:11:10', 100, 1, 1);