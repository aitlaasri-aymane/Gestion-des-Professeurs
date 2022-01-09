-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 09, 2022 at 10:33 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_gestProfs`
--

-- --------------------------------------------------------

--
-- Table structure for table `departement`
--

CREATE TABLE `departement` (
  `id_depart` int(11) NOT NULL,
  `nomDepart` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `departement`
--

INSERT INTO `departement` (`id_depart`, `nomDepart`) VALUES
(1, 'Math info'),
(2, 'Mecanique'),
(7, 'Electrique');

-- --------------------------------------------------------

--
-- Table structure for table `Profs`
--

CREATE TABLE `Profs` (
  `id_prof` int(11) NOT NULL,
  `departement` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `cin` varchar(50) NOT NULL,
  `adresse` varchar(50) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `date_recrutement` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Profs`
--

INSERT INTO `Profs` (`id_prof`, `departement`, `nom`, `prenom`, `cin`, `adresse`, `telephone`, `email`, `date_recrutement`) VALUES
(19, 1, 'Anter', 'Karim', 'EE997788', 'Casablanca Ain Sbaa', '06611775544', 'anter.k92@gmail.com', '2022-01-04'),
(24, 2, 'Mortaki', 'Hicham', 'EE554321', 'Mohammedia b3id chwiya', '0677223344', 'HakimKhojir67@gmail.com', '2022-01-04'),
(41, 1, 'AIT LAASRI', 'Aymane', 'EE443322', 'Marrakech Massira 1', '0674537617', 'aymaneaitlaasri@gmail.com', '2022-01-11');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `departement`
--
ALTER TABLE `departement`
  ADD PRIMARY KEY (`id_depart`);

--
-- Indexes for table `Profs`
--
ALTER TABLE `Profs`
  ADD PRIMARY KEY (`id_prof`),
  ADD KEY `departement` (`departement`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `departement`
--
ALTER TABLE `departement`
  MODIFY `id_depart` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Profs`
--
ALTER TABLE `Profs`
  MODIFY `id_prof` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Profs`
--
ALTER TABLE `Profs`
  ADD CONSTRAINT `profs_ibfk_1` FOREIGN KEY (`departement`) REFERENCES `departement` (`id_depart`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
