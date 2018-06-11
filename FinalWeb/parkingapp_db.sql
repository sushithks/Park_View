# MySQL-Front 3.2  (Build 8.0)

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES latin1 */;

# Host: localhost    Database: parkingapp_db
# ------------------------------------------------------
# Server version 5.1.43-community

DROP DATABASE IF EXISTS `parkingapp_db`;
CREATE DATABASE `parkingapp_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `parkingapp_db`;

#
# Table structure for table area_admin_register_tbl
#

CREATE TABLE `area_admin_register_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parking_name` varchar(30) DEFAULT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(11) NOT NULL,
  `email_id` varchar(30) NOT NULL,
  `phone_number` varchar(30) NOT NULL,
  `lat` varchar(50) NOT NULL DEFAULT '0',
  `longitude` varchar(50) NOT NULL,
  `number_for_two_wheeler` varchar(11) NOT NULL,
  `two_wheeler_for_1hr` varchar(11) NOT NULL,
  `two_wheeler_after_1hr` varchar(11) NOT NULL,
  `number_for_four_wheeler` varchar(11) NOT NULL,
  `four_wheeler_for_1hr` varchar(11) NOT NULL,
  `four_wheeler_after_1hr` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

#
# Dumping data for table area_admin_register_tbl
#

INSERT INTO `area_admin_register_tbl` VALUES (1,'Think Force','think','think','think@gmail.com','1234567898','9.966702888755375','76.29809115082026','20','10','5','30','15','15');
INSERT INTO `area_admin_register_tbl` VALUES (3,'kad','kad','kad','kad@gmail.com','1234567990','9.989338430295684','76.57993298023939','6','20','10','30','15','10');


#
# Table structure for table bank_tbl
#

CREATE TABLE `bank_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_name` varchar(20) NOT NULL,
  `cvv_no` int(11) NOT NULL DEFAULT '0',
  `exp_date` varchar(11) NOT NULL,
  `balance` varchar(11) NOT NULL DEFAULT '0',
  `card_no` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

#
# Dumping data for table bank_tbl
#

INSERT INTO `bank_tbl` VALUES (1,'anoop',222,'Jan/2020','11750','12345');


#
# Table structure for table booking_tbl
#

CREATE TABLE `booking_tbl` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `pa_id` varchar(11) NOT NULL,
  `user_id` varchar(11) NOT NULL,
  `booking_time` varchar(50) NOT NULL,
  `for_time` varchar(50) NOT NULL,
  `slot_type` varchar(20) NOT NULL,
  `booking_status` varchar(20) NOT NULL DEFAULT 'pending',
  `price` varchar(4) DEFAULT '0.00',
  `token` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

#
# Dumping data for table booking_tbl
#

INSERT INTO `booking_tbl` VALUES (1,'1','1','23/09/2017 09:40:42 PM','23/09/2017 11:40:42 AM','car','cancel','0.00',0);
INSERT INTO `booking_tbl` VALUES (2,'1','1','22/09/2017 09:40:42 PM','22/09/2017 09:40:42 PM','car','cancel','0.00',0);
INSERT INTO `booking_tbl` VALUES (3,'1','2','13/10/2017 06:14:15 PM','13/10/2017 6:25:00 PM','car','cancel','7.5',0);
INSERT INTO `booking_tbl` VALUES (4,'1','2','01/11/2017 04:49:40 PM','01/11/2017 4:56:00 PM','car','cancel','7.5',2);
INSERT INTO `booking_tbl` VALUES (5,'1','2','01/11/2017 05:00:13 PM','01/11/2017 5:15:00 PM','car','cancel','7.5',2);
INSERT INTO `booking_tbl` VALUES (6,'1','2','01/11/2017 05:01:14 PM','01/11/2017 5:20:00 PM','car','pending','0.00',2);
INSERT INTO `booking_tbl` VALUES (7,'1','2','01/11/2017 05:01:23 PM','01/11/2017 5:25:00 PM','car','pending','0.00',3);
INSERT INTO `booking_tbl` VALUES (8,'3','2','01/11/2017 05:02:14 PM','01/11/2017 5:25:00 PM','car','pending','0.00',1);


#
# Table structure for table login_tbl
#

CREATE TABLE `login_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(30) NOT NULL,
  `password` varchar(11) NOT NULL,
  `user_type` varchar(25) NOT NULL,
  `status` varchar(11) DEFAULT NULL,
  `fcm` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

#
# Dumping data for table login_tbl
#

INSERT INTO `login_tbl` VALUES (1,'admin','admin','admin','A',NULL);
INSERT INTO `login_tbl` VALUES (2,'g','g','PARKING AREA ADMIN','P',NULL);
INSERT INTO `login_tbl` VALUES (3,'think','think','PARKING AREA ADMIN','A',NULL);
INSERT INTO `login_tbl` VALUES (4,'manu','manu','PARK EM USER','A','e4Nr2RjrqPc:APA91bEmfad0-RTvDWfpB4WdYzlwLu8xhLNEGeBFouPuFT3AmiliOj7V6j3JP5kR1yC1nISGm9_ey2kOsmTAvFJm8tZ6p4XruoCu0kHD1KpMUiG8xvip5Xz8zaMIgFKJvWVD3cQdmCLt');
INSERT INTO `login_tbl` VALUES (5,'madh','mad','PARK EM USER','A','dpHMiQig3uM:APA91bEIRqEuCIOcQDuE4HSSunacd04elovqw1uq2ByCnJsI_9ZiKXll9OZV_-U0bMpvi4xxCIzhDfKtGtQ9HdEYYQzOgmF4L5VI5fmUHayYx5ECHENcEYCLGG6ECT7lBCM7EQ6p34Hm');
INSERT INTO `login_tbl` VALUES (6,'kar','kar','PARKING AREA ADMIN','P',NULL);


#
# Table structure for table parking_tbl
#

CREATE TABLE `parking_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(11) DEFAULT NULL,
  `check_in_time` varchar(50) DEFAULT 'NA',
  `check_out_time` varchar(50) DEFAULT 'NA',
  `amount` varchar(11) DEFAULT NULL,
  `slot_type` varchar(30) DEFAULT NULL,
  `booking_id` varchar(11) DEFAULT NULL,
  `p_id` int(11) DEFAULT '0',
  `cash_mode` varchar(20) DEFAULT 'NA',
  `feed_back` varchar(250) NOT NULL DEFAULT 'NA',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

#
# Dumping data for table parking_tbl
#

INSERT INTO `parking_tbl` VALUES (1,'1','13/09/2017 08:11:42','13/09/2017 08:11:48','15.0','car',NULL,1,'online','NA');
INSERT INTO `parking_tbl` VALUES (2,'1','13/09/2017 08:14:24','13/09/2017 08:17:56','15.0','car',NULL,1,'NA','NA');
INSERT INTO `parking_tbl` VALUES (3,'1','13/09/2017 08:20:04','13/09/2017 08:20:17','10.0','bike',NULL,1,'cash','NA');
INSERT INTO `parking_tbl` VALUES (4,'1','22/09/2017 07:56:35','22/09/2017 08:50:52','30.0','car',NULL,2,'online','jhggh khgjj jjbb');


#
# Table structure for table user_register_tbl
#

CREATE TABLE `user_register_tbl` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `email_id` varchar(30) NOT NULL,
  `phone_number` varchar(21) NOT NULL,
  `balance` varchar(11) NOT NULL,
  `qrcode_path` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

#
# Dumping data for table user_register_tbl
#

INSERT INTO `user_register_tbl` VALUES (1,'Manav Krishnan','manu','manu@gmail.com','987456123','10000','QrCode\\manu\\QRCode.png');
INSERT INTO `user_register_tbl` VALUES (2,'madhav','madh','mad@gmail.com','1234567890','200','QrCode\\madh\\QRCode.png');


#
# Table structure for table wallet_tbl
#

CREATE TABLE `wallet_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL DEFAULT '0',
  `balance` varchar(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

#
# Dumping data for table wallet_tbl
#

INSERT INTO `wallet_tbl` VALUES (1,'think','457.5');
INSERT INTO `wallet_tbl` VALUES (2,'manu','2527.5');
INSERT INTO `wallet_tbl` VALUES (3,'madh','220');
INSERT INTO `wallet_tbl` VALUES (4,'kar','0');
INSERT INTO `wallet_tbl` VALUES (5,'kad','0');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
