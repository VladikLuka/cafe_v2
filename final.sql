-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: epam-cafe
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `address_city` varchar(45) DEFAULT NULL,
  `address_street` varchar(45) DEFAULT NULL,
  `address_house` varchar(45) DEFAULT NULL,
  `address_flat` varchar(45) DEFAULT NULL,
  `address_user_id` int NOT NULL,
  `address_isAvailable` tinyint NOT NULL DEFAULT '1',
  `address_is_available` bit(1) DEFAULT NULL,
  UNIQUE KEY `address_id_UNIQUE` (`address_id`),
  KEY `fk_address_users_idx` (`address_id`),
  KEY `fk_address_user_idx` (`address_user_id`),
  CONSTRAINT `fk_address_user` FOREIGN KEY (`address_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(45) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_id_UNIQUE` (`category_id`),
  KEY `category_name_idx` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='Current table contains and describes dish categories.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Drink'),(3,'Garnish'),(5,'Meat'),(4,'Pizza'),(2,'Salad'),(6,'Sushi');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dish` (
  `dish_id` int NOT NULL AUTO_INCREMENT,
  `dish_name` varchar(60) NOT NULL,
  `dish_description` tinytext,
  `dish_price` decimal(6,2) NOT NULL,
  `dish_isAvailable` tinyint(1) NOT NULL DEFAULT '1',
  `categories_category_id` int NOT NULL,
  `dish_weight` int NOT NULL,
  `dish_picture_path` varchar(100) NOT NULL,
  `dish_is_available` bit(1) DEFAULT NULL,
  PRIMARY KEY (`dish_id`),
  UNIQUE KEY `menu_dishName_UNIQUE` (`dish_name`),
  KEY `menu_isEnable_idx` (`dish_isAvailable`),
  KEY `menu_dish_price` (`dish_price`),
  KEY `fk_dishes_categories_idx` (`categories_category_id`),
  CONSTRAINT `fk_dishes_categories` FOREIGN KEY (`categories_category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8 COMMENT='Current table contains list of dishes. Fields: name of dish, description, weight, price, and information concerning availability.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES (1,'Chicken thigh shish kebab','onions, parsley, pita bread, chicken thighs, onions',24.00,1,5,400,'/static/img/meat/chicken_thigh_shish_kebab.png',NULL),(2,'Nigiri with seared tuna','Seared tuna, tuna shavings, mayonnaise, Thai sweet chili sauce, wasabi',3.10,1,6,38,'/static/img/sushi/nigiri_with_marinade_tuna.png',NULL),(3,'Greek salad','Greek salad is made with pieces of tomatoes, sliced cucumbers, onion',12.90,1,2,250,'/static/img/salad/greece.png',NULL),(5,'Nigiri with pickled tuna','Tuna marinated in soy sauce, mayonnaise, black flying fish roe, wasabi',3.10,1,6,38,'/static/img/sushi/nigiri_with_roast_tuna.png',NULL),(6,'Lamb kebabs with rib','onion, parsley, pita bread, lamb, onion',26.00,1,5,400,'/static/img/meat/lamb_skewers_with_ribs.png',NULL),(7,'Pepperoni','Barbecue sauce, Pepperoni, Mozzarella cheese, Tomatoes',10.00,1,4,200,'/static/img/pizza/pepperoni.png',NULL),(8,'Five cheeses','Feta, Parmesan, Blue cheese, Fresh Cream, Mozzarella cheese, Chedder',26.00,1,4,595,'/static/img/pizza/5_syrov.png',NULL),(9,'Barbecue','Barbecue sauce, Champignon, Mozzarella cheese, Bacon, Chicken, Onion',24.80,1,4,590,'/static/img/pizza/barbecue.png',NULL),(10,'Barbecue delux','Onion, Parmesan, Boiled pork, Barbecue sauce, Bell pepper, Mozzarella cheese, Champignon',28.80,1,4,660,'/static/img/pizza/barbecue_deluxe.png',NULL),(11,'Carbonara','Onion, Mozzarella cheese, Fresh Cream, Bacon, Ham, Champignon',25.80,1,4,610,'/static/img/pizza/carbonara.png',NULL),(12,'Eksravaganzza','Veal, Champignon, Tomato Sauce, Pepperoni,Olives, Ham',28.80,1,4,810,'/static/img/pizza/eksravaganzza.png',NULL),(13,'Hawaiian','Chicken, Tomato Sauce, Mozzarella cheese, Pineapple',21.80,1,4,625,'/static/img/pizza/gavayskaya.png',NULL),(14,'Country','Garlic Sauce, Onion, Ham, Mozzarella cheese, Cucumbers, Champignon, Bacon',26.80,1,4,655,'/static/img/pizza/kantri.png',NULL),(15,'Dzimenta','lettuce, dried meat, mozzarella, cucumber, olives, arugula',22.80,1,2,230,'/static/img/salad/dzimenta.png',NULL),(16,'Breeze','lettuce, shrimp, mussels, bell peppers, arugula, cucumber',22.30,1,2,321,'/static/img/salad/breeze.png',NULL),(17,'Coca-cola','',3.40,1,1,500,'/static/img/drink/cola_0.5.png',NULL),(18,'Fanta','',3.40,1,1,500,'/static/img/drink/fanta_0.5.png',NULL),(19,'Fuze tea','',3.40,1,1,500,'/static/img/drink/fuze_tea_0.5.png',NULL),(20,'Fuze tea green','',3.40,1,1,500,'/static/img/drink/fuze_tea_green_0.5.png',NULL),(21,'Dzijon','lettuce, mushroom mix, bacon, egg, Dijon spinach sauce',12.50,1,2,325,'/static/img/salad/dzijon.png',NULL),(22,'Tun','lettuce, cucumber, tuna, chicken egg, bacon, asparagus beans',13.90,1,2,300,'/static/img/salad/tun.png',NULL),(23,'Healthy fish','quinoa, baked salmon, cherries, raisins, arugula, spinach',16.90,1,2,370,'/static/img/salad/hfish.png',NULL),(24,'Ocheana','lettuce, salmon, cream cheese, cucumber, tomatoes, egg',13.65,1,2,355,'/static/img/salad/ocheana.png',NULL),(25,'Marina','lettuce, tuna, mussels, feta in herbs, baked vegetables, arugula',13.20,1,2,480,'/static/img/salad/marina.png',NULL),(26,'Sprite',NULL,3.40,1,1,500,'/static/img/drink/sprite.png',NULL),(27,'Bonaqua',NULL,2.80,1,1,500,'/static/img/drink/bonaqua.png',NULL),(28,'Cherri juice',NULL,3.40,1,1,1000,'/static/img/drink/juice_cherry.png',NULL),(29,'Multi juice',NULL,3.40,1,1,1000,'/static/img/drink/juice_multi.png',NULL),(30,'Apple juice',NULL,3.40,1,1,1000,'/static/img/drink/juice_apple.png',NULL),(31,'Orange juice',NULL,3.50,1,1,1000,'/static/img/drink/juice_orange.png',NULL),(32,'Tomato juice',NULL,3.20,1,1,1000,'/static/img/drink/juice_tomato.png',NULL),(33,'Nigiri with seared perch','Scorched sea bass, mayonnaise, Thai sweet chili sauce, cashew nut, wasabi',3.10,1,6,38,'/static/img/sushi/nigiri_with_roast_basse.png',NULL),(34,'Nigiri with pickled perch','Sea bass pickled in soy sauce, flying fish roe orange, mayonnaise, wasabi',1.90,1,6,38,'/static/img/sushi/nigiri_with_marinade_tuna.png',NULL),(35,'Chicken wings skewers','onions, parsley, pita bread, chicken, onions',25.00,1,5,400,'/static/img/meat/chicken_wings_skewers.png',NULL),(36,'Carbonar','Onion, Mozzarella cheese, Fresh Cream, Bacon, Ham, Champignon',25.80,1,4,610,'/static/img/pizza/carbonara.png',NULL),(37,'Hawaiiana','Chicken, Tomato Sauce, Mozzarella cheese, Pineapple',21.80,1,4,625,'/static/img/pizza/gavayskaya.png',NULL),(38,'Four cheeses','Feta, Parmesan, Blue cheese, Fresh Cream, Mozzarella cheese, Chedder',26.00,1,4,595,'/static/img/pizza/5_syrov.png',NULL),(39,'Pepperoni lux','Barbecue sauce, Pepperoni, Mozzarella cheese, Tomatoes',10.00,1,4,200,'/static/img/pizza/pepperoni.png',NULL),(40,'Barbecue new','Onion, Parmesan, Boiled pork, Barbecue sauce, Bell pepper, Mozzarella cheese, Champignon',28.80,1,4,660,'/static/img/pizza/barbecue_deluxe.png',NULL),(41,'Nigiri with seared salmon','Scorched salmon, pickled Takuan radish, Thai sweet chili sauce, mayonnaise, Nori seaweed, wasabi',2.90,1,6,38,'/static/img/sushi/nigiri_with_roast_salmon.png',NULL),(42,'Nigiri with pickled salmon','Salmon marinated in soy sauce, mayonnaise, flying red fish roe, wasabi',2.90,1,6,38,'/static/img/sushi/nigiri_with_marinade_bass.png',NULL),(43,'Abi','Tiger shrimp, mayonnaise, flying red fish roe, hiyashi wakame seaweed, tofu cheese',3.10,1,6,38,'/static/img/sushi/ebi_new.png',NULL),(44,'Tai','Sea bass, Teriyaki sauce, sesame seeds, wasabi, hiyashi wakame seaweed, tofu cheese',3.10,1,6,38,'/static/img/sushi/tai.png',NULL),(45,'Syake','Salmon, Japanese omelet, Teriyaki sauce, hiyashi wakame seaweed, tofu cheese',3.10,1,6,38,'/static/img/sushi/syace_new.png',NULL),(46,'Maguro','Tuna, wasabi,  curd cheese, Masago caviar, wasabi',2.80,1,6,38,'/static/img/sushi/maguro_new.png',NULL),(47,'Unagi','Smoked eel, Teriyaki sauce, sesame seeds, wasabi',3.20,1,6,34,'/static/img/sushi/unagi_new.png',NULL),(48,'Tamago','Japanese omelet, Teriyaki sauce, sesame seeds, wasabi',1.50,1,6,44,'/static/img/sushi/tamago_new.png',NULL),(49,'Spicy tecca maki','Tuna, spicy sauce, wasabi, Mamenori soy paper, wasabi',0.60,1,6,115,'/static/img/sushi/spicy_tekka_maki_b.png',NULL),(50,'Syake Maki','Salmon, wasabi, Tiger shrimp, cucumber, tomato,',0.60,1,6,115,'/static/img/sushi/syace_maki_b.png',NULL),(51,'Thekka Maki','Tuna, wasabi, avocado, cottage cheese, pickled Takuan radish',0.70,1,6,110,'/static/img/sushi/tekka_maki_b.png',NULL),(52,'Avocado Maki','Avocado, mayonnaise, wasabi, Salmon, avocado, curd cheese',0.55,1,6,110,'/static/img/sushi/avocado_maki_b.png',NULL),(53,'Syake Avocado Maki','Salmon, Avocado, Wasabi, hiyashi wakame seaweed, tofu cheese',0.55,1,6,110,'/static/img/sushi/syace_avocado_maki_b.png',NULL),(54,'Kappa maki','Cucumber, Sesame, avocado, cottage cheese, pickled Takuan radish',0.60,1,6,135,'/static/img/sushi/kappa_maki_b.png',NULL),(55,'Unagi maki','Smoked eel, cucumber, wasabi, Salmon, avocado, curd cheese,',0.70,1,6,145,'/static/img/sushi/unagi_maki_b.png',NULL),(56,'Spicy kunsei maki','Smoked salmon, green onion, spicy sauce, wasabi',0.80,1,6,135,'/static/img/sushi/spicy_kunsei_maki_b.png',NULL),(57,'Ensyoku maki','Tiger shrimp, curd cheese, Masago caviar, wasabi',0.80,1,6,122,'/static/img/sushi/Ensecu_maki_b.png',NULL),(58,'Katori maki','Salmon, avocado, curd cheese, Mamenori soy paper, wasabi',0.70,1,6,122,'/static/img/sushi/katori_maki_b.png',NULL),(59,'Syake Origami Maki','Salmon, curd cheese, hiyashi wakame seaweed, tofu cheese, black flying fish roe',1.00,1,6,178,'/static/img/sushi/syace_origami_maki_b.png',NULL),(60,'Arizona maki','Tiger shrimp, cucumber, tomato, mayonnaise, sesame, wasabi',1.20,1,6,210,'/static/img/sushi/arizona_maki_b.png',NULL),(61,'Asahi Maki','Tiger prawn, tempura squid, avocado, curd cheese, tuna flakes, wasabi',1.30,1,6,210,'/static/img/sushi/asahi_maki_b.png',NULL),(62,'Bunsai Maki','Chicken fillet in Teriyaki sauce, avocado, cottage cheese, pickled Takuan radish',1.50,1,6,220,'/static/img/sushi/bunsai_maki_b.png',NULL),(63,'Kaiso Maki','Tiger shrimp, salmon, curd cheese, hiyashi wakame seaweed, wasabi',1.60,1,6,230,'/static/img/sushi/kayso_maki_b.png',NULL),(64,'California poppies','Crab sticks, avocado, cucumber, mayonnaise, orange flying fish roe, wasabi',1.50,1,6,220,'/static/img/sushi/california_maki_b.png',NULL),(65,'Beef skewers','onions, parsley, pita bread, beef skewers, onions',24.50,1,5,450,'/static/img/meat/beef_skewers.png',NULL),(66,'Pork and beef kebab','onions, parsley, pita bread, beef, pork, onions',27.00,1,5,550,'/static/img/meat/pork_and_beef_kebab.png',NULL),(67,'Potato pancakes with sour cream','potato pancakes, sour cream, greens',6.00,1,3,160,'/static/img/garnish/potato_with_sour.png',NULL),(68,'Bolognese','homemade pasta, minced meat, tomato, cherry, arugula sauce, Parmesan cheese',9.00,1,3,390,'/static/img/garnish/bolonez.png',NULL),(72,'Meat pasta','homemade pasta, jerky, tomato, raspberry, cherry, ruccola sauce',8.00,1,3,420,'/static/img/garnish/meat_pasta.png',NULL),(73,'Pasta from shef','homemade pasta, bacon, chick breast, mushrooms, brand sauce, cherry, ruccola',7.50,1,3,420,'/static/img/garnish/shef_pasta.png',NULL),(74,'Pasta carbonara','homemade pasta, bacon, chick breast, mushrooms, brand sauce, cherry, ruccola',9.00,1,3,450,'/static/img/garnish/carbonara.png',NULL),(93,'asd','faf',21.00,0,4,12,'/static/img/upload/c55KbY4Z3Ro.jpg',NULL),(94,'ыфафа','фыва',321.00,0,6,12,'/static/img/upload/c55KbY4Z3Ro.jpg',NULL);
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_status` enum('PAID','VIOLATED','CANCELED','EXPECTED','CLOSED') NOT NULL DEFAULT 'EXPECTED',
  `order_receipt_time` datetime NOT NULL,
  `order_delivery_time` datetime DEFAULT NULL,
  `order_credit_time` datetime DEFAULT NULL,
  `order_payment_method` enum('BALANCE','CASH','CARD','CREDIT') NOT NULL DEFAULT 'CASH',
  `order_rating` enum('1','2','3','4','5') DEFAULT NULL,
  `order_review` tinytext,
  `users_ownerId` int DEFAULT NULL,
  `address_address_id` int DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `order_receipt_time_idx` (`order_receipt_time`),
  KEY `order_status_idx` (`order_status`),
  KEY `fk_orders_address_idx` (`address_address_id`),
  KEY `fk_orders_users_idx` (`users_ownerId`),
  CONSTRAINT `fk_orders_address` FOREIGN KEY (`address_address_id`) REFERENCES `address` (`address_id`),
  CONSTRAINT `fk_orders_users` FOREIGN KEY (`users_ownerId`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_dishes`
--

DROP TABLE IF EXISTS `orders_dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_dishes` (
  `orders_has_dishes_quantity` tinyint NOT NULL DEFAULT '1',
  `orders_order_id` int NOT NULL,
  `dishes_dish_id` int NOT NULL,
  PRIMARY KEY (`orders_order_id`,`dishes_dish_id`),
  KEY `fk_orders_has_dishes_orders_idx` (`orders_order_id`),
  KEY `kf_orders_has_dishes_dish_idx` (`dishes_dish_id`),
  CONSTRAINT `fk_orders_has_dishes_dish` FOREIGN KEY (`dishes_dish_id`) REFERENCES `dish` (`dish_id`),
  CONSTRAINT `fk_orders_has_dishes_orders` FOREIGN KEY (`orders_order_id`) REFERENCES `orders_dishes` (`orders_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Many-to-many relation. Order can contain lots of different dishes with different portion quantity.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_dishes`
--

LOCK TABLES `orders_dishes` WRITE;
/*!40000 ALTER TABLE `orders_dishes` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders_dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL,
  `role_name` enum('USER','ADMIN') NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `user_surname` varchar(45) DEFAULT NULL,
  `user_phone` varchar(45) NOT NULL,
  `user_email` varchar(320) NOT NULL,
  `user_password` varchar(40) NOT NULL,
  `user_money` decimal(10,2) NOT NULL DEFAULT '0.00',
  `user_loyaltyPoints` mediumint NOT NULL DEFAULT '0',
  `roles_role_id` int NOT NULL DEFAULT '2',
  `user_isBan` tinyint NOT NULL DEFAULT '0',
  `user_isCredit` tinyint DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_email_UNIQUE` (`user_email`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_phone_UNIQUE` (`user_phone`),
  KEY `fk_user_role_idx` (`roles_role_id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`roles_role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-14 10:44:38
