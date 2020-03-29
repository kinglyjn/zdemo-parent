
DROP TABLE IF EXISTS `T_BOOK_STOCK`;
CREATE TABLE `T_BOOK_STOCK` (
  `ISBN` varchar(255) COLLATE utf8_bin NOT NULL,
  `STOCK` int(11) DEFAULT NULL,
  PRIMARY KEY (`ISBN`)
);

INSERT INTO T_BOOK_STOCK(ISBN, STOCK) VALUES('1001', 10);
INSERT INTO T_BOOK_STOCK(ISBN, STOCK) VALUES('1002', 10);


DROP TABLE IF EXISTS `T_BOOK`;
CREATE TABLE `T_BOOK` (
  `ISBN` varchar(255) COLLATE utf8_bin NOT NULL,
  `BOOK_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PRICE` float DEFAULT NULL,
  PRIMARY KEY (`ISBN`)
);

INSERT INTO T_BOOK(ISBN, BOOK_NAME, PRICE) VALUES('1001', 'Java', 100);
INSERT INTO T_BOOK(ISBN, BOOK_NAME, PRICE) VALUES('1002', 'Oracle', 70);


DROP TABLE IF EXISTS `T_ACCOUNT`;
CREATE TABLE `T_ACCOUNT` (
  `USERNAME` varchar(255) COLLATE utf8_bin NOT NULL,
  `BALANCE` int(11) DEFAULT NULL,
  PRIMARY KEY (`USERNAME`)
);

INSERT INTO T_ACCOUNT(USERNAME, BALANCE) VALUES('AA', 300);





