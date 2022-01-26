DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
    `id`  INTEGER PRIMARY KEY AUTOINCREMENT,
    `user_name` varchar(20) NOT NULL,
    `password` varchar(10) NOT NULL
);
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
    `id`  INTEGER PRIMARY KEY AUTOINCREMENT,
    `sender` INTEGER NOT NULL,
    `recipient` INTEGER NOT NULL,
    `ttype` INTEGER NOT NULL,
    `url` varchar(30),
    `text` varchar(30),
    `time` date
);
