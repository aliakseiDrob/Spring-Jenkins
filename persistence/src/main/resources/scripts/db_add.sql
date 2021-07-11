CREATE SCHEMA `secure_db_certificates` DEFAULT CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS `secure_db_certificates`.`gift_certificate`
(
    `id`               BIGINT        NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(255)  NOT NULL,
    `description`      VARCHAR(255)  NOT NULL,
    `price`            DECIMAL(6, 2) NOT NULL,
    `duration`         INT           NOT NULL,
    `is_available`     INT           not null,
    `create_date`      timestamp,
    `last_update_date` timestamp,
    primary key (id)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `secure_db_certificates`.`tag`
(
    `id`   BIGINT              NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) unique NOT NULL,
    primary key (id)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `secure_db_certificates`.`gift_certificate_tag`
(
    `gift_certificate_id` BIGINT,
    `tag_id`              BIGINT,
    primary key (`gift_certificate_id`, `tag_id`),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
) ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `secure_db_certificates`.`users`
(
    `id`       BIGINT              NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(255) unique not null,
    `password` varchar(255)        not null,
    primary key (id)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `secure_db_certificates`.`roles`
(
    `id`   BIGINT NOT NULL auto_increment,
    `role` enum ('ROLE_ADMIN','ROLE_USER'),
    primary key (id)
)
    ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `secure_db_certificates`.`users_role`
(
    `users_id` BIGINT,
    `role_id`  BIGINT,
    primary key (`users_id`, `role_id`),
    FOREIGN KEY (users_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `secure_db_certificates`.`orders`
(
    `id`             BIGINT        NOT NULL AUTO_INCREMENT,
    `date`           timestamp     NOT NULL,
    `order_cost`     DECIMAL(6, 2) NOT NULL,
    `user_id`        BIGINT,
    `certificate_id` BIGINT,
    primary key (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (certificate_id) references gift_certificate (id)
)
    ENGINE = InnoDB;