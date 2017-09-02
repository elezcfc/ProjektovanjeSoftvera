/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.5-10.1.21-MariaDB : Database - baza_kafic
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`baza_kafic` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `baza_kafic`;

/*Table structure for table `konobar` */

DROP TABLE IF EXISTS `konobar`;

CREATE TABLE `konobar` (
  `konobarID` int(11) NOT NULL AUTO_INCREMENT,
  `jmbg` varchar(10) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `username` varchar(10) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`konobarID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

/*Data for the table `konobar` */

insert  into `konobar`(`konobarID`,`jmbg`,`name`,`username`,`password`) values (1,'12345678','Stefan','stefan','stefan'),(34,'456987213','Jelica','jelica','jelica'),(38,'87654321','Pera','pera12','pera12');

/*Table structure for table `pice` */

DROP TABLE IF EXISTS `pice`;

CREATE TABLE `pice` (
  `piceID` int(11) NOT NULL AUTO_INCREMENT,
  `nazivPica` varchar(20) DEFAULT NULL,
  `cena` double DEFAULT NULL,
  PRIMARY KEY (`piceID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

/*Data for the table `pice` */

insert  into `pice`(`piceID`,`nazivPica`,`cena`) values (1,'Coca cola',120),(4,'Jelen pivo',100),(5,'Turska kafa',80),(6,'Cockta',130),(7,'Espresso',120),(8,'Jack Daniels',210),(9,'Vodka',170),(10,'Jin',150),(11,'Mohito',190);

/*Table structure for table `racun` */

DROP TABLE IF EXISTS `racun`;

CREATE TABLE `racun` (
  `racunID` int(11) NOT NULL AUTO_INCREMENT,
  `iznos` double DEFAULT NULL,
  `konobarID` int(11) DEFAULT NULL,
  `stoID` int(11) DEFAULT NULL,
  `placen` int(1) DEFAULT NULL,
  PRIMARY KEY (`racunID`),
  KEY `konobarID` (`konobarID`),
  KEY `racun_ibfk_2` (`stoID`),
  CONSTRAINT `racun_ibfk_1` FOREIGN KEY (`konobarID`) REFERENCES `konobar` (`konobarID`),
  CONSTRAINT `racun_ibfk_2` FOREIGN KEY (`stoID`) REFERENCES `sto` (`stoID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

/*Data for the table `racun` */

insert  into `racun`(`racunID`,`iznos`,`konobarID`,`stoID`,`placen`) values (31,720,1,1,1),(32,1980,34,1,1);

/*Table structure for table `stavkaracuna` */

DROP TABLE IF EXISTS `stavkaracuna`;

CREATE TABLE `stavkaracuna` (
  `racunID` int(11) NOT NULL,
  `brStavkeRacuna` int(11) NOT NULL,
  `piceID` int(11) DEFAULT NULL,
  `kolicina` int(20) NOT NULL,
  PRIMARY KEY (`racunID`,`brStavkeRacuna`),
  KEY `brStavkeRacuna` (`brStavkeRacuna`),
  KEY `stavkaracuna_ibfk_2` (`piceID`),
  CONSTRAINT `stavkaracuna_ibfk_1` FOREIGN KEY (`racunID`) REFERENCES `racun` (`racunID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stavkaracuna_ibfk_2` FOREIGN KEY (`piceID`) REFERENCES `pice` (`piceID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

/*Data for the table `stavkaracuna` */

insert  into `stavkaracuna`(`racunID`,`brStavkeRacuna`,`piceID`,`kolicina`) values (31,1,1,3),(31,2,7,3),(32,1,1,6),(32,2,8,6);

/*Table structure for table `sto` */

DROP TABLE IF EXISTS `sto`;

CREATE TABLE `sto` (
  `stoID` int(11) NOT NULL AUTO_INCREMENT,
  `ukupanDnevniIznos` double DEFAULT NULL,
  `zauzet` int(1) DEFAULT NULL,
  PRIMARY KEY (`stoID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

/*Data for the table `sto` */

insert  into `sto`(`stoID`,`ukupanDnevniIznos`,`zauzet`) values (1,2700,0),(12,0,0),(14,0,0),(18,0,0),(19,0,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
