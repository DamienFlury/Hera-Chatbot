CREATE TABLE `localisation` (
  `language` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `value` varchar(1000) NOT NULL,
   PRIMARY KEY (`language`, `name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
