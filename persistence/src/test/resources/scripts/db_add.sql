DROP TABLE IF EXISTS gift_certificate;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS gift_certificate_tag;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS audit;


CREATE TABLE gift_certificate
(
    `id`               BIGINT        NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(50)   NOT NULL,
    `description`      VARCHAR(200)  NOT NULL,
    `price`            DECIMAL(6, 2) NOT NULL,
    `duration`         INT           NOT NULL,
    `is_available`     INT           not null,
    `create_date`      timestamp,
    `last_update_date` timestamp,
    primary key (id)
);

CREATE TABLE tag
(
    `id`   BIGINT             NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) unique NOT NULL CHECK (name <> ''),
    primary key (id)
);

CREATE TABLE gift_certificate_tag
(
    `gift_certificate_id` BIGINT,
    `tag_id`              BIGINT,
    primary key (`gift_certificate_id`, `tag_id`),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);
CREATE TABLE users
(
    `id`       BIGINT             NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(45) unique not null,
    `password` varchar(255)       not null,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS `roles`
(
    `id`   BIGINT NOT NULL auto_increment,
    `role` enum ('ROLE_ADMIN','ROLE_USER'),
    primary key (id)
);
CREATE TABLE IF NOT EXISTS `users_role`
(
    `users_id` BIGINT,
    `role_id`  BIGINT,
    primary key (`users_id`, `role_id`),
    FOREIGN KEY (users_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `orders`
(
    `id`             BIGINT        NOT NULL AUTO_INCREMENT,
    `date`           timestamp     NOT NULL,
    `order_cost`     DECIMAL(6, 2) NOT NULL,
    `user_id`        BIGINT,
    `certificate_id` BIGINT,
    primary key (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (certificate_id) references gift_certificate (id)
);
CREATE TABLE IF NOT EXISTS `audit`
(
    `id`        BIGINT    NOT NULL auto_increment,
    `operation` varchar(50),
    `date`      timestamp NOT NULL,
    `entity`    varchar(500),
    primary key (id)
);