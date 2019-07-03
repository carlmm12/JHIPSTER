CREATE DATABASE  IF NOT EXISTS `samtel` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `samtel`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: samtel
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.40-MariaDB

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
-- Table structure for table `autor`
--

DROP TABLE IF EXISTS `autor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `web` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autor`
--

LOCK TABLES `autor` WRITE;
/*!40000 ALTER TABLE `autor` DISABLE KEYS */;
/*!40000 ALTER TABLE `autor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `autor_libro`
--

DROP TABLE IF EXISTS `autor_libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autor_libro` (
  `autor_id` bigint(20) NOT NULL,
  `libro_id` bigint(20) NOT NULL,
  PRIMARY KEY (`autor_id`,`libro_id`),
  KEY `FKir6vkirqsvatb6wgtw4wgb880` (`libro_id`),
  CONSTRAINT `FKetoacnupugngmuot6q1mtmsoq` FOREIGN KEY (`autor_id`) REFERENCES `autor` (`id`),
  CONSTRAINT `FKir6vkirqsvatb6wgtw4wgb880` FOREIGN KEY (`libro_id`) REFERENCES `libro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autor_libro`
--

LOCK TABLES `autor_libro` WRITE;
/*!40000 ALTER TABLE `autor_libro` DISABLE KEYS */;
/*!40000 ALTER TABLE `autor_libro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `direccion` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'cll 181 bis  # 15-45','carlmm12@gmail.com','carl',6681567),(2,'Cr 7 # 129-30','jhonsanchezts@gmail.com','jhon',6785671),(3,'Cr 7 # 129-30','alonso@gmail.com','Alonso',6785671);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,'\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ejemplar`
--

DROP TABLE IF EXISTS `ejemplar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ejemplar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `edicion` varchar(255) NOT NULL,
  `encuadernacion` varchar(255) NOT NULL,
  `precio` double NOT NULL,
  `cliente_id` bigint(20) DEFAULT NULL,
  `libro_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdtc09omqg23gsdt1ke25ea7ri` (`cliente_id`),
  KEY `FKoylmccipqn9njnujra1qg0sq2` (`libro_id`),
  CONSTRAINT `FKdtc09omqg23gsdt1ke25ea7ri` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FKoylmccipqn9njnujra1qg0sq2` FOREIGN KEY (`libro_id`) REFERENCES `libro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ejemplar`
--

LOCK TABLES `ejemplar` WRITE;
/*!40000 ALTER TABLE `ejemplar` DISABLE KEYS */;
/*!40000 ALTER TABLE `ejemplar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_authority`
--

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_persistent_audit_event`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_date` datetime DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  `principal` varchar(255) NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_persistent_audit_event`
--

LOCK TABLES `jhi_persistent_audit_event` WRITE;
/*!40000 ALTER TABLE `jhi_persistent_audit_event` DISABLE KEYS */;
INSERT INTO `jhi_persistent_audit_event` VALUES (1,'2019-06-28 16:42:52','AUTHENTICATION_SUCCESS','web_app'),(2,'2019-06-28 16:42:56','AUTHENTICATION_FAILURE','carl'),(3,'2019-06-28 17:26:57','AUTHENTICATION_SUCCESS','web_app'),(4,'2019-06-28 17:27:00','AUTHENTICATION_SUCCESS','heider'),(5,'2019-06-28 17:30:36','AUTHENTICATION_SUCCESS','web_app'),(6,'2019-06-28 17:30:37','AUTHENTICATION_SUCCESS','heider'),(7,'2019-06-28 17:30:39','AUTHENTICATION_SUCCESS','heider'),(8,'2019-06-28 17:30:39','AUTHENTICATION_SUCCESS','heider'),(9,'2019-06-28 17:31:05','AUTHENTICATION_SUCCESS','web_app'),(10,'2019-06-28 17:31:06','AUTHENTICATION_FAILURE','admin'),(11,'2019-06-28 17:31:13','AUTHENTICATION_SUCCESS','web_app'),(12,'2019-06-28 17:31:13','AUTHENTICATION_FAILURE','admin'),(13,'2019-06-28 17:31:17','AUTHENTICATION_SUCCESS','web_app'),(14,'2019-06-28 17:31:17','AUTHENTICATION_FAILURE','admin'),(15,'2019-06-28 17:31:18','AUTHENTICATION_SUCCESS','web_app'),(16,'2019-06-28 17:31:18','AUTHENTICATION_FAILURE','admin'),(17,'2019-06-28 17:51:46','AUTHENTICATION_SUCCESS','web_app'),(18,'2019-06-28 17:58:41','AUTHENTICATION_SUCCESS','web_app'),(19,'2019-06-28 17:58:50','AUTHENTICATION_SUCCESS','heider'),(20,'2019-06-28 17:59:00','AUTHENTICATION_SUCCESS','web_app'),(21,'2019-06-28 17:59:02','AUTHENTICATION_FAILURE','heidder'),(22,'2019-06-28 17:59:02','AUTHENTICATION_SUCCESS','web_app'),(23,'2019-06-28 17:59:02','AUTHENTICATION_FAILURE','heidder'),(24,'2019-06-28 17:59:08','AUTHENTICATION_SUCCESS','web_app'),(25,'2019-06-28 17:59:08','AUTHENTICATION_SUCCESS','heider'),(26,'2019-06-28 17:59:09','AUTHENTICATION_SUCCESS','heider'),(27,'2019-06-28 18:00:47','AUTHENTICATION_SUCCESS','web_app'),(28,'2019-06-28 18:00:47','AUTHENTICATION_FAILURE','admin'),(29,'2019-06-28 19:04:34','AUTHENTICATION_SUCCESS','web_app'),(30,'2019-06-28 19:04:49','AUTHENTICATION_SUCCESS','web_app'),(31,'2019-06-28 19:04:50','AUTHENTICATION_SUCCESS','heider'),(32,'2019-06-28 19:04:54','AUTHENTICATION_SUCCESS','heider'),(33,'2019-07-02 20:47:47','AUTHENTICATION_SUCCESS','web_app'),(34,'2019-07-02 20:47:57','AUTHENTICATION_SUCCESS','heider'),(35,'2019-07-02 20:53:45','AUTHENTICATION_SUCCESS','web_app'),(36,'2019-07-02 20:53:46','AUTHENTICATION_SUCCESS','heider'),(37,'2019-07-02 22:29:26','AUTHENTICATION_SUCCESS','web_app'),(38,'2019-07-02 22:29:28','AUTHENTICATION_FAILURE','admin'),(39,'2019-07-02 22:29:28','AUTHENTICATION_SUCCESS','web_app'),(40,'2019-07-02 22:29:29','AUTHENTICATION_FAILURE','admin'),(41,'2019-07-02 22:29:38','AUTHENTICATION_SUCCESS','web_app'),(42,'2019-07-02 22:29:38','AUTHENTICATION_SUCCESS','heider'),(43,'2019-07-02 22:29:39','AUTHENTICATION_SUCCESS','heider'),(44,'2019-07-02 22:53:21','AUTHENTICATION_SUCCESS','web_app');
/*!40000 ALTER TABLE `jhi_persistent_audit_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_persistent_audit_evt_data`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_evt_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`event_id`,`name`),
  CONSTRAINT `FK2ehnyx2si4tjd2nt4q7y40v8m` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_persistent_audit_evt_data`
--

LOCK TABLES `jhi_persistent_audit_evt_data` WRITE;
/*!40000 ALTER TABLE `jhi_persistent_audit_evt_data` DISABLE KEYS */;
INSERT INTO `jhi_persistent_audit_evt_data` VALUES (1,'0:0:0:0:0:0:0:1','remoteAddress'),(2,'{grant_type=password, scope=openid, username=carl}','details'),(2,'Bad credentials','message'),(2,'org.springframework.security.authentication.BadCredentialsException','type'),(3,'0:0:0:0:0:0:0:1','remoteAddress'),(4,'{grant_type=password, scope=openid, username=heider}','details'),(5,'192.165.30.63','remoteAddress'),(6,'{grant_type=password, username=heider}','details'),(7,'remoteAddress=192.165.30.63, tokenType=BearertokenValue=<TOKEN>','details'),(8,'remoteAddress=192.165.30.63, tokenType=BearertokenValue=<TOKEN>','details'),(9,'192.165.30.63','remoteAddress'),(10,'{grant_type=password, username=admin}','details'),(10,'Bad credentials','message'),(10,'org.springframework.security.authentication.BadCredentialsException','type'),(11,'192.165.30.63','remoteAddress'),(12,'{grant_type=password, username=admin}','details'),(12,'Bad credentials','message'),(12,'org.springframework.security.authentication.BadCredentialsException','type'),(13,'192.165.30.63','remoteAddress'),(14,'{grant_type=password, username=admin}','details'),(14,'Bad credentials','message'),(14,'org.springframework.security.authentication.BadCredentialsException','type'),(15,'192.165.30.63','remoteAddress'),(16,'{grant_type=password, username=admin}','details'),(16,'Bad credentials','message'),(16,'org.springframework.security.authentication.BadCredentialsException','type'),(17,'192.165.30.63','remoteAddress'),(18,'192.165.30.63','remoteAddress'),(19,'remoteAddress=192.165.30.63, tokenType=BearertokenValue=<TOKEN>','details'),(20,'192.165.30.63','remoteAddress'),(21,'{grant_type=password, username=heidder}','details'),(21,'Bad credentials','message'),(21,'org.springframework.security.authentication.BadCredentialsException','type'),(22,'192.165.30.63','remoteAddress'),(23,'{grant_type=password, username=heidder}','details'),(23,'Bad credentials','message'),(23,'org.springframework.security.authentication.BadCredentialsException','type'),(24,'192.165.30.63','remoteAddress'),(25,'{grant_type=password, username=heider}','details'),(26,'remoteAddress=192.165.30.63, tokenType=BearertokenValue=<TOKEN>','details'),(27,'192.165.30.63','remoteAddress'),(28,'{grant_type=password, username=admin}','details'),(28,'Bad credentials','message'),(28,'org.springframework.security.authentication.BadCredentialsException','type'),(29,'192.165.30.63','remoteAddress'),(30,'192.165.30.63','remoteAddress'),(31,'{grant_type=password, username=heider}','details'),(32,'remoteAddress=192.165.30.63, tokenType=BearertokenValue=<TOKEN>','details'),(33,'0:0:0:0:0:0:0:1','remoteAddress'),(34,'{grant_type=password, scope=openid, username=heider}','details'),(35,'0:0:0:0:0:0:0:1','remoteAddress'),(36,'{grant_type=password, scope=openid, username=heider}','details'),(37,'192.165.30.110','remoteAddress'),(38,'{grant_type=password, username=admin}','details'),(38,'Bad credentials','message'),(38,'org.springframework.security.authentication.BadCredentialsException','type'),(39,'192.165.30.110','remoteAddress'),(40,'{grant_type=password, username=admin}','details'),(40,'Bad credentials','message'),(40,'org.springframework.security.authentication.BadCredentialsException','type'),(41,'192.165.30.110','remoteAddress'),(42,'{grant_type=password, username=heider}','details'),(43,'remoteAddress=192.165.30.110, tokenType=BearertokenValue=<TOKEN>','details'),(44,'192.165.30.110','remoteAddress');
/*!40000 ALTER TABLE `jhi_persistent_audit_evt_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user`
--

DROP TABLE IF EXISTS `jhi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `email` varchar(254) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `reset_date` datetime DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9y0frpqnmqe7y6mk109vw3246` (`login`),
  UNIQUE KEY `UK_bycanyquvi09q7fh5pgxrqnku` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user`
--

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` VALUES (1,'anonymousUser','2019-06-28 16:46:52','anonymousUser','2019-06-28 16:46:52','\0','09122884938934833607','carlmm12@gmail.com','carl',NULL,'es','mora','carl','$2a$10$gpW9L5OYpYjtqL2JWtSk9O0bP/jA44BVt/OAIvz9VB.v8Sog.KUii',NULL,NULL),(2,'anonymousUser','2019-06-28 17:18:00','anonymousUser','2019-06-28 17:22:08','',NULL,'hforerom@unal.edu.co','Heidergger',NULL,'en','Forero','heider','$2a$10$qTSb0DDR6MOrMLfshfFucupm2H29THANQe83U9it7oH8svXtZf0Jy',NULL,NULL),(48,'anonymousUser','2019-07-02 16:50:48','anonymousUser','2019-07-02 16:50:48','\0','02423212140717083667','jhonsanchezts@gmail.com','jhon',NULL,'en','sanchez','jhon','$2a$10$dRng06srywShLOX1GFOWeOevAG.GPdD9EId2NWN6dceFiDBPoxnJ2',NULL,NULL);
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `FK4psxl0jtx6nr7rhqbynr6itoc` (`authority_name`),
  CONSTRAINT `FK290okww5jujghp4el5i7mgwu0` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`),
  CONSTRAINT `FK4psxl0jtx6nr7rhqbynr6itoc` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user_authority`
--

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro`
--

DROP TABLE IF EXISTS `libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libro` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `editorial` varchar(255) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libro`
--

LOCK TABLES `libro` WRITE;
/*!40000 ALTER TABLE `libro` DISABLE KEYS */;
/*!40000 ALTER TABLE `libro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-03  0:03:54
