-- MySQL dump 10.13  Distrib 5.6.21, for Win32 (x86)
--
-- Host: localhost    Database: payroll
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `Employee_Number` int(10) unsigned NOT NULL,
  `idEmployee` int(11) NOT NULL,
  `Last_Name` varchar(45) NOT NULL,
  `First_Name` varchar(45) NOT NULL,
  `SSN` decimal(10,0) NOT NULL,
  `Pay_Rate` varchar(40) DEFAULT NULL,
  `PayRates_id` int(11) DEFAULT NULL,
  `Vacation_Days` int(11) DEFAULT NULL,
  `Paid_To_Date` decimal(2,0) DEFAULT NULL,
  `Paid_Last_Year` decimal(2,0) DEFAULT NULL,
  PRIMARY KEY (`Employee_Number`),
  UNIQUE KEY `Employee Number_UNIQUE` (`Employee_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1001,1,'Tuan','Huynh',201482421,'3.0',1,5,99,99),(1002,2,'Dao','Nguyen',401287954,'3.33',2,10,99,99);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_rates`
--

DROP TABLE IF EXISTS `pay_rates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pay_rates` (
  `idPay_Rates` int(11) NOT NULL,
  `Pay_Rate_Name` varchar(40) NOT NULL,
  `Value` decimal(10,0) NOT NULL,
  `Tax_Percentage` decimal(10,0) NOT NULL,
  `Pay_Type` int(11) NOT NULL,
  `Pay_Amount` decimal(10,0) NOT NULL,
  `PT_Level_C` decimal(10,0) NOT NULL,
  PRIMARY KEY (`idPay_Rates`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_rates`
--

LOCK TABLES `pay_rates` WRITE;
/*!40000 ALTER TABLE `pay_rates` DISABLE KEYS */;
INSERT INTO `pay_rates` VALUES (1,'Staff',500000,9,1,7000000,2),(2,'Manager',10000000,10,2,15000000,3);
/*!40000 ALTER TABLE `pay_rates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `User_ID` int(10) NOT NULL AUTO_INCREMENT,
  `User_Name` varchar(255) NOT NULL,
  `Password` text NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'tuanhuynh','123456','huynhhoangtuan@duytan.edu.vn',1),(2,'anhdao','654321','anhdao@gmail.com',1),(3,'hoangkun','123456987','hoangkun@gmail.com',1),(4,'kiochip','789456123','kiochip@gmail.com',1),(5,'hoanghuy','123456987','hoanghuy@gmail.com',1),(6,'kenhuynh','789456123','kenhuynh@gmail.com',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-26 20:37:35
