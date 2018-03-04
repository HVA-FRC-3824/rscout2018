-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: rscout
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `2018scmbmatchschedule`
--

DROP TABLE IF EXISTS `2018scmbmatchschedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `2018scmbmatchschedule` (
  `matchNumber` int(11) DEFAULT NULL,
  `blue1` int(11) DEFAULT NULL,
  `blue2` int(11) DEFAULT NULL,
  `blue3` int(11) DEFAULT NULL,
  `red1` int(11) DEFAULT NULL,
  `red2` int(11) DEFAULT NULL,
  `red3` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `2018scmbmatchschedule`
--

LOCK TABLES `2018scmbmatchschedule` WRITE;
/*!40000 ALTER TABLE `2018scmbmatchschedule` DISABLE KEYS */;
INSERT INTO `2018scmbmatchschedule` VALUES (1,1553,4504,281,4243,2383,4582),(10,6961,3651,4965,4748,283,7097),(11,4823,1287,6693,5410,1319,4267),(12,2393,2815,4083,1398,4020,5472),(13,6222,5063,4504,4481,5327,2614),(14,2187,4243,4075,3824,125,7085),(15,4451,3651,281,1553,4265,3489),(16,5130,1319,4073,4847,6167,4748),(17,4013,4965,5410,1758,4074,3641),(18,5317,1369,900,6366,7097,342),(19,3976,4823,2383,1539,3410,6961),(2,4481,3140,5130,4075,6626,1539),(20,1051,1287,4582,283,6626,5022),(21,3490,263,5777,343,3140,1923),(22,3824,1876,6693,2152,4481,4020),(23,4073,5317,3651,4243,5063,1758),(24,3489,281,7085,342,5472,5130),(25,283,4267,125,3641,1553,3410),(26,4748,343,4083,1539,6222,4074),(27,4582,3140,4847,6961,2187,1287),(28,3490,1923,4823,4504,1398,6366),(29,6626,1319,1876,900,4013,263),(3,900,5327,3489,3490,4083,2152),(30,4451,4075,3976,2152,5327,5410),(31,4965,7097,5022,4265,5777,2393),(32,2383,1369,2614,2815,1051,6167),(33,4074,4267,5472,4073,1539,4504),(34,4013,4481,342,1287,4243,3490),(35,5317,4020,1553,5130,5410,343),(36,6693,1398,4965,5063,900,125),(37,5022,3410,263,7097,4075,4847),(38,4582,7085,2614,3651,4083,1369),(39,6167,3489,1923,2815,3976,6626),(4,342,2815,4267,2393,263,4451),(40,6366,2187,3641,1051,4451,1876),(41,3824,2152,2383,6222,2393,283),(42,1319,4265,6961,5327,5777,1758),(43,4748,4823,5063,281,3140,4020),(44,4504,343,1287,4075,4073,2815),(45,6167,3641,4481,263,342,3651),(46,4013,1923,1553,4847,2393,3824),(47,4074,3976,5327,7097,4582,1398),(48,2614,5410,2187,3489,4965,3410),(49,1051,3490,1539,5317,281,6693),(5,4020,4265,6222,4074,1051,4847),(50,4265,283,3140,7085,6366,1319),(51,900,5472,4451,5777,2383,6222),(52,4083,5022,125,1876,6961,5130),(53,1758,6626,2152,1369,4267,4748),(54,4823,4074,3489,4243,2393,6167),(55,3140,3824,1051,342,3410,5327),(56,4073,900,4481,4265,3641,4582),(57,4020,1539,2815,5022,6366,4013),(58,4267,1398,5777,1876,2614,4748),(59,283,263,4243,6693,5130,3976),(6,343,2187,1398,5022,6693,1369),(60,3651,125,5472,4823,4847,5410),(61,4083,7097,1553,2187,1758,4504),(62,1287,1369,4075,281,1923,6222),(63,6961,4451,6626,5063,3490,7085),(64,1319,4965,2152,343,5317,2383),(65,6366,4847,4265,6693,3489,4267),(66,3140,900,5022,3976,4504,3651),(67,3410,5777,1369,1553,4481,4074),(68,2393,4073,5063,5410,1051,4083),(69,6222,4823,6626,2187,342,5317),(7,5063,6167,3410,5472,1876,4013),(70,4451,1539,7097,1319,3824,1398),(71,7085,4748,1287,125,2383,263),(72,5327,281,1876,4965,283,2815),(73,5472,4243,343,3641,6961,1923),(74,1758,4020,3490,4582,4075,6167),(75,5130,4013,4265,2614,2152,4823),(76,2383,6693,6366,6626,4083,5777),(77,263,6222,3976,4965,1553,4073),(78,5327,3641,2393,1369,7085,1539),(79,3410,4748,5317,4504,4582,4451),(8,1923,2614,6366,125,1758,4073),(80,1923,4074,5063,2152,5022,2187),(81,5410,3824,281,6167,900,6961),(82,4020,2614,4243,1287,5472,1319),(83,4847,342,1758,1876,343,283),(84,125,5130,2815,3140,3490,7097),(85,1398,4013,1051,3651,3489,4075),(86,4267,6167,5327,4481,5022,5317),(87,263,6366,3824,5472,4582,5063),(88,4083,4847,4243,1369,4451,4965),(89,3410,5130,2393,1758,1287,900),(9,7085,5317,3641,5777,3976,3824),(90,4075,6961,342,2614,6693,4074),(91,1398,2152,4073,6626,1553,3140),(92,281,4748,4481,3976,4013,2187),(93,3641,3651,1319,2815,6222,3490),(94,7097,7085,1051,4267,343,4823),(95,3489,4504,283,5777,125,4020),(96,2383,1876,1539,1923,5410,4265);
/*!40000 ALTER TABLE `2018scmbmatchschedule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-02 11:16:51
