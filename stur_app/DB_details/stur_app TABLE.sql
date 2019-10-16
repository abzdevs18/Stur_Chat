-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2019 at 10:05 AM
-- Server version: 10.1.39-MariaDB
-- PHP Version: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stur_app`
--
CREATE DATABASE IF NOT EXISTS `stur_app` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `stur_app`;

-- --------------------------------------------------------

--
-- Table structure for table `email_address`
--

CREATE TABLE `email_address` (
  `p_k` int(11) NOT NULL,
  `user_pk` int(11) NOT NULL,
  `email_add` varchar(100) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `email_address`
--

INSERT INTO `email_address` (`p_k`, `user_pk`, `email_add`, `status`) VALUES
(1, 1, 'debian@gmail.com', 1),
(2, 2, 'kai@gmail.com', 1);

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `p_k` int(11) NOT NULL,
  `reciever` int(11) NOT NULL,
  `sender` int(11) NOT NULL,
  `content` text NOT NULL,
  `date` varchar(50) NOT NULL,
  `time` varchar(50) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`p_k`, `reciever`, `sender`, `content`, `date`, `time`, `timestamp`) VALUES
(1, 2, 1, 'hello miga nako', 'May 22, 2019', '4:01 pm', '2019-05-22 08:01:05'),
(2, 1, 2, 'oh  jeey', 'May 22, 2019', '4:01 pm', '2019-05-22 08:01:55');

-- --------------------------------------------------------

--
-- Table structure for table `profile_images`
--

CREATE TABLE `profile_images` (
  `p_k` int(11) NOT NULL,
  `user_pk` int(11) NOT NULL,
  `image_path` varchar(250) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `profile_images`
--

INSERT INTO `profile_images` (`p_k`, `user_pk`, `image_path`, `status`) VALUES
(1, 1, 'com.microvirt.launcher.png', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tmp_msgs`
--

CREATE TABLE `tmp_msgs` (
  `p_k` int(11) NOT NULL,
  `reciever` int(11) NOT NULL,
  `sender` int(11) NOT NULL,
  `typing` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `p_k` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `password` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`p_k`, `username`, `lastname`, `password`) VALUES
(1, 'debian', 'zzeref', '$2y$10$DGGiOC4Y70thb9Ytrb4YB.Cmg5WnCck0Z1N6QTfAra9scrnBiK8si'),
(2, 'kalipha', 'kali', '$2y$10$QypLPJkRG2W2wBsHQsjjtOFPk2hm/BNFPSFia628Nns0/m6AWkLza');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `email_address`
--
ALTER TABLE `email_address`
  ADD PRIMARY KEY (`p_k`),
  ADD KEY `user_pk` (`user_pk`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`p_k`),
  ADD KEY `reciever` (`reciever`),
  ADD KEY `sender` (`sender`);

--
-- Indexes for table `profile_images`
--
ALTER TABLE `profile_images`
  ADD PRIMARY KEY (`p_k`),
  ADD KEY `user_pk` (`user_pk`);

--
-- Indexes for table `tmp_msgs`
--
ALTER TABLE `tmp_msgs`
  ADD PRIMARY KEY (`p_k`),
  ADD KEY `reciever` (`reciever`),
  ADD KEY `sender` (`sender`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`p_k`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `email_address`
--
ALTER TABLE `email_address`
  MODIFY `p_k` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `p_k` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `profile_images`
--
ALTER TABLE `profile_images`
  MODIFY `p_k` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tmp_msgs`
--
ALTER TABLE `tmp_msgs`
  MODIFY `p_k` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `p_k` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `email_address`
--
ALTER TABLE `email_address`
  ADD CONSTRAINT `email_address_ibfk_1` FOREIGN KEY (`user_pk`) REFERENCES `users` (`p_k`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`reciever`) REFERENCES `users` (`p_k`),
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`sender`) REFERENCES `users` (`p_k`);

--
-- Constraints for table `profile_images`
--
ALTER TABLE `profile_images`
  ADD CONSTRAINT `profile_images_ibfk_1` FOREIGN KEY (`user_pk`) REFERENCES `users` (`p_k`);

--
-- Constraints for table `tmp_msgs`
--
ALTER TABLE `tmp_msgs`
  ADD CONSTRAINT `tmp_msgs_ibfk_1` FOREIGN KEY (`reciever`) REFERENCES `users` (`p_k`),
  ADD CONSTRAINT `tmp_msgs_ibfk_2` FOREIGN KEY (`sender`) REFERENCES `users` (`p_k`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
