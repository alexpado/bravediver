
CREATE TABLE `stratagem`
(
    `id`           INT AUTO_INCREMENT NOT NULL,
    `name`         VARCHAR(255)       NOT NULL,
    `type`         VARCHAR(255)       NOT NULL,
    `currency`     VARCHAR(255)       NOT NULL,
    `cost`         INT                NOT NULL,
    `unlock_level` INT                NOT NULL,
    `image`        VARCHAR(255)       NOT NULL,
    CONSTRAINT `pk_stratagem` PRIMARY KEY (`id`)
);

CREATE TABLE `user`
(
    `id`       BIGINT       NOT NULL,
    `username` VARCHAR(255) NOT NULL,
    CONSTRAINT `pk_user` PRIMARY KEY (`id`)
);

CREATE TABLE `user_stratagems`
(
    `user_id`       BIGINT NOT NULL,
    `stratagems_id` INT    NOT NULL,
    CONSTRAINT `pk_user_stratagems` PRIMARY KEY (`user_id`, `stratagems_id`)
);

ALTER TABLE `user_stratagems`
    ADD CONSTRAINT `fk_usestr_on_stratagem` FOREIGN KEY (`stratagems_id`) REFERENCES `stratagem` (`id`);

ALTER TABLE `user_stratagems`
    ADD CONSTRAINT `fk_usestr_on_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
