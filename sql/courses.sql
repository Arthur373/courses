drop database courses;

create database courses;

use courses;

CREATE TABLE IF NOT EXISTS `applicants`
(
    `id`           bigint UNSIGNED primary key auto_increment,
    `name`         varchar(191) NOT NULL,
    `email`        varchar(191) NOT NULL,
    `phone_number` varchar(191) NOT NULL,
    `address`      varchar(191) NOT NULL,
    `status`       varchar(191) NOT NULL,
    `course_id`    int(10) UNSIGNED DEFAULT NULL,
    constraint course_id_foreign_key foreign key (course_id) references courses (id)
);

INSERT INTO `applicants` (`name`, `email`, `phone_number`, `address`,`status`,`course_id`) VALUES
('arthur', 'arthur@mail.ru', '77777777', 'isahakyan','COMPLETED',1),
('armen', 'armen@mail.ru', '33333333', 'shahumyan','IN_PROGRESS',1);


CREATE TABLE IF NOT EXISTS `courses`
(
    `id`           int(10) UNSIGNED NOT NULL,
    `name`         int(10) UNSIGNED      DEFAULT NULL,
    `description`  varchar(191)     NOT NULL,
    `teacher_name` varchar(191)     NOT NULL,
    `start_date`   timestamp        NULL DEFAULT NULL,
    `end_date`     timestamp        NULL DEFAULT NULL
);

INSERT INTO `courses` (`name`, `description`, `teacher_name`,`start_date`,`end_date`)
VALUES ('Java', 'Java 8 month courses', 'Vazgen','2021-03-20 11:14:55','2021-05-20 13:14:55'),
       ('Web', 'Web 8 month courses', 'Tiko','2021-03-20 11:14:55','2021-07-20 13:14:55');

CREATE TABLE IF NOT EXISTS `users` (
    `id`         bigint UNSIGNED primary key auto_increment,
    `email`      varchar(191)  NOT NULL,
    `password`   varchar(191)  NOT NULL
);

INSERT INTO `users` (`email`, `password`) VALUES
('arthur@mail.ru','$2a$10$1IKBKP8QIEF6D2Vjb0drqOieb3k4UsAcyy9axapWanxKM9Yt41yta'),
('john@mail.ru','$2a$10$1IKBKP8QIEF6D2Vjb0drqOieb3k4UsAcyy9axapWanxKM9Yt41yta');
