INSERT INTO gift_certificate (name, description, price, duration, is_available,create_date, last_update_date)
values ('first', 'for men', 100, 4,1, '2021-05-22 20:11:10', '2021-05-24 20:11:10'),
       ('second', 'for men', 100, 7,1, '2021-05-22 20:11:10', '2021-05-24 20:11:10'),
       ('third', 'for women', 230, 17,1, '2021-05-22 20:11:10', '2021-05-28 20:11:10'),
       ('first', 'for women', 340, 8,1, '2021-05-21 20:11:10', '2021-05-24 20:11:10'),
       ('fifth', 'for children', 25, 11,0, '2021-05-23 20:11:10', '2021-05-24 16:11:10'),
       ('sixth', 'for children', 50, 14,1, '2021-05-18 20:11:10', '2021-05-19 22:11:10');

INSERT INTO tag (name)
VALUES ('health'),
       ('games'),
       ('sport'),
       ('hobby'),
       ('reading'),
       ('quiz');

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 4),
       (2, 6),
       (2, 1),
       (3, 3),
       (3, 4),
       (4, 1),
       (4, 5),
       (5, 2),
       (5, 5),
       (6, 6),
       (6, 3);

INSERT INTO users (name,password)
VALUES ('IVAN','$2y$12$CqcetN2pD7SikXv0ea3td.O/LNxfQUEhp.Ql/3WSFlbXH6Wb6IbAu'),
       ('Petr','$2y$12$FhX/W5PrqIE3JYB92jAIoe6FfK6so0F9DREi.Abu2sFGXAv82k01.'),
       ('Stepan','$2y$12$NfsRZgFpuIa7Hv2nZJrh3uYI7kQpxWWVWUDrutZ.1Uc3UheW1GLfy'),
       ('Fedor','$2y$12$c28mVPcOr1hQYMRUocYP6upB2RcUsYEnzEjYQj2v813hml1feMLbm'),
       ('Mike','$2y$12$GkJY.pO4xcWEjEnP6v/kJeiW8PLObx1XOcYNKjugwK9S4y4bl/Qra');
INSERT INTO ROLES (role)
VALUES('ROLE_ADMIN'),
      ('ROLE_USER');
insert INTO users_role values
(1,1),
(2,2),
(3,2),
(4,2),
(5,2);

INSERT INTO `orders` (date,order_cost,user_id,certificate_id)
VALUES ('2021-05-24 20:11:10',340,1,1),
       ('2021-05-30 20:11:10',100,1,1),
       ('2021-05-24 20:11:10',100,1,2),
       ('2021-05-29 20:11:10',230,1,3),
       ('2021-05-24 20:11:10',340,2,4),
       ('2021-05-24 20:11:10',25,3,5);