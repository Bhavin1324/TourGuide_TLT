-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 22, 2023 at 12:25 PM
-- Server version: 10.3.34-MariaDB-0ubuntu0.20.04.1
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `TLTDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `bus_master`
--

CREATE TABLE `bus_master` (
  `Id` varchar(500) NOT NULL,
  `BusType` text DEFAULT NULL,
  `CityId` varchar(500) DEFAULT NULL,
  `PlaceId` varchar(500) DEFAULT NULL,
  `BusNumber` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `bus_stop`
--

CREATE TABLE `bus_stop` (
  `Id` varchar(500) NOT NULL,
  `Name` text DEFAULT NULL,
  `BusNumber` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `city_master`
--

CREATE TABLE `city_master` (
  `Id` varchar(500) NOT NULL,
  `Name` varchar(500) DEFAULT NULL,
  `StateId` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `country_master`
--

CREATE TABLE `country_master` (
  `Id` varchar(500) NOT NULL,
  `Name` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `guide_master`
--

CREATE TABLE `guide_master` (
  `Id` varchar(500) NOT NULL,
  `Name` text DEFAULT NULL,
  `Gender` text DEFAULT NULL,
  `Email` text DEFAULT NULL,
  `Amount` decimal(9,2) DEFAULT NULL,
  `ContactNo` text DEFAULT NULL,
  `PlaceId` varchar(500) DEFAULT NULL,
  `UserId` varchar(500) DEFAULT NULL,
  `VisitStatus` text DEFAULT NULL,
  `IsAppointed` bit(1) DEFAULT NULL,
  `AppiontmentStartedAt` datetime DEFAULT NULL,
  `AppiontmentEndedAt` datetime DEFAULT NULL,
  `PaymentMethodId` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `payment_master`
--

CREATE TABLE `payment_master` (
  `Id` varchar(500) NOT NULL,
  `CreatedAt` datetime DEFAULT NULL,
  `CardDetails` text DEFAULT NULL,
  `PaymentStatus` text DEFAULT NULL,
  `GuideId` varchar(500) DEFAULT NULL,
  `Username` varchar(500) DEFAULT NULL,
  `SubscriptionId` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `payment_method`
--

CREATE TABLE `payment_method` (
  `Id` varchar(500) NOT NULL,
  `Name` text DEFAULT NULL,
  `Description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `place_category`
--

CREATE TABLE `place_category` (
  `Id` varchar(500) NOT NULL,
  `Name` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `place_master`
--

CREATE TABLE `place_master` (
  `Id` varchar(500) NOT NULL,
  `Name` text DEFAULT NULL,
  `Address` text DEFAULT NULL,
  `Longitude` text DEFAULT NULL,
  `Latitude` text DEFAULT NULL,
  `OpeningTime` datetime DEFAULT NULL,
  `ClosingTime` datetime DEFAULT NULL,
  `Description` mediumtext DEFAULT NULL,
  `CategoryId` varchar(500) DEFAULT NULL,
  `CityId` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `project_role`
--

CREATE TABLE `project_role` (
  `Role` varchar(100) NOT NULL,
  `Username` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `state_master`
--

CREATE TABLE `state_master` (
  `Id` varchar(500) NOT NULL,
  `Name` varchar(500) DEFAULT NULL,
  `CountryId` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `subscription_master`
--

CREATE TABLE `subscription_master` (
  `Id` varchar(500) NOT NULL,
  `StartDate` datetime DEFAULT NULL,
  `EndDate` datetime DEFAULT NULL,
  `PaymentMethodId` varchar(500) DEFAULT NULL,
  `SubscriptionModelId` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `subscription_model`
--

CREATE TABLE `subscription_model` (
  `Id` varchar(500) NOT NULL,
  `Name` text DEFAULT NULL,
  `DurationInMonth` int(11) DEFAULT NULL,
  `Cost` decimal(9,2) DEFAULT NULL,
  `Description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `user_master`
--

CREATE TABLE `user_master` (
  `Username` varchar(500) NOT NULL,
  `Name` text DEFAULT NULL,
  `Password` text DEFAULT NULL,
  `Email` text DEFAULT NULL,
  `RoleId` text DEFAULT NULL,
  `HasSubscription` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bus_master`
--
ALTER TABLE `bus_master`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `BusNumber` (`BusNumber`),
  ADD KEY `CityBus_FK` (`CityId`),
  ADD KEY `PlaceBus_FK` (`PlaceId`);

--
-- Indexes for table `bus_stop`
--
ALTER TABLE `bus_stop`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Bus_BusNumber_FK` (`BusNumber`);

--
-- Indexes for table `city_master`
--
ALTER TABLE `city_master`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `City_State_FK` (`StateId`);

--
-- Indexes for table `country_master`
--
ALTER TABLE `country_master`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `guide_master`
--
ALTER TABLE `guide_master`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `payment_master`
--
ALTER TABLE `payment_master`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `User_Payment_FK` (`Username`),
  ADD KEY `Guide_Payment_FK` (`GuideId`),
  ADD KEY `Subscription_Payment_FK` (`SubscriptionId`);

--
-- Indexes for table `payment_method`
--
ALTER TABLE `payment_method`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `place_category`
--
ALTER TABLE `place_category`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `place_master`
--
ALTER TABLE `place_master`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `CityPlace_FK` (`CityId`),
  ADD KEY `PlaceCategory_FK` (`CategoryId`);

--
-- Indexes for table `project_role`
--
ALTER TABLE `project_role`
  ADD PRIMARY KEY (`Role`,`Username`),
  ADD KEY `fk_projectrole_idx` (`Username`);

--
-- Indexes for table `state_master`
--
ALTER TABLE `state_master`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Country_State_FK` (`CountryId`);

--
-- Indexes for table `subscription_master`
--
ALTER TABLE `subscription_master`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `SubModel_FK` (`SubscriptionModelId`),
  ADD KEY `PaymentMethod_Sub_FK` (`PaymentMethodId`);

--
-- Indexes for table `subscription_model`
--
ALTER TABLE `subscription_model`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `user_master`
--
ALTER TABLE `user_master`
  ADD PRIMARY KEY (`Username`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bus_master`
--
ALTER TABLE `bus_master`
  ADD CONSTRAINT `CityBus_FK` FOREIGN KEY (`CityId`) REFERENCES `city_master` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `PlaceBus_FK` FOREIGN KEY (`PlaceId`) REFERENCES `place_master` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `bus_stop`
--
ALTER TABLE `bus_stop`
  ADD CONSTRAINT `Bus_BusNumber_FK` FOREIGN KEY (`BusNumber`) REFERENCES `bus_master` (`BusNumber`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `city_master`
--
ALTER TABLE `city_master`
  ADD CONSTRAINT `City_State_FK` FOREIGN KEY (`StateId`) REFERENCES `state_master` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `payment_master`
--
ALTER TABLE `payment_master`
  ADD CONSTRAINT `Guide_Payment_FK` FOREIGN KEY (`GuideId`) REFERENCES `guide_master` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Subscription_Payment_FK` FOREIGN KEY (`SubscriptionId`) REFERENCES `subscription_master` (`Id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  ADD CONSTRAINT `User_Payment_FK` FOREIGN KEY (`Username`) REFERENCES `user_master` (`Username`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `place_master`
--
ALTER TABLE `place_master`
  ADD CONSTRAINT `CityPlace_FK` FOREIGN KEY (`CityId`) REFERENCES `city_master` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `PlaceCategory_FK` FOREIGN KEY (`CategoryId`) REFERENCES `place_category` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `project_role`
--
ALTER TABLE `project_role`
  ADD CONSTRAINT `fk_projectrole_1` FOREIGN KEY (`Username`) REFERENCES `user_master` (`Username`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `state_master`
--
ALTER TABLE `state_master`
  ADD CONSTRAINT `Country_State_FK` FOREIGN KEY (`CountryId`) REFERENCES `country_master` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `subscription_master`
--
ALTER TABLE `subscription_master`
  ADD CONSTRAINT `PaymentMethod_Sub_FK` FOREIGN KEY (`PaymentMethodId`) REFERENCES `payment_method` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `SubModel_FK` FOREIGN KEY (`SubscriptionModelId`) REFERENCES `subscription_model` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
