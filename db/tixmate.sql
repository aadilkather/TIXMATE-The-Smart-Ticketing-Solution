-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 15, 2020 at 05:22 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `tixmate`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_tbl`
--

CREATE TABLE IF NOT EXISTS `account_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `account_no` varchar(200) NOT NULL,
  `amount` varchar(200) NOT NULL,
  `status` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `account_tbl`
--

INSERT INTO `account_tbl` (`id`, `userid`, `account_no`, `amount`, `status`) VALUES
(1, 1, '1344616125', '485', 'active');

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE IF NOT EXISTS `bookings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bus_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `seats` varchar(200) NOT NULL,
  `bseat` int(11) NOT NULL,
  `pickup` varchar(200) NOT NULL,
  `pickup_time` varchar(200) NOT NULL,
  `dropoff` varchar(200) NOT NULL,
  `route_id` varchar(20) NOT NULL,
  `price` varchar(200) NOT NULL,
  `bdate` varchar(50) NOT NULL,
  `btime` varchar(50) NOT NULL,
  `status` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`id`, `bus_id`, `user_id`, `seats`, `bseat`, `pickup`, `pickup_time`, `dropoff`, `route_id`, `price`, `bdate`, `btime`, `status`) VALUES
(1, 1, 1, '0', 3, 'Thamaraparambu', '09:40:00', 'Naval Base', '11', '33', '08-04-2020', '09:41:36', 'completed');

-- --------------------------------------------------------

--
-- Table structure for table `buses`
--

CREATE TABLE IF NOT EXISTS `buses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bus_no` varchar(20) NOT NULL,
  `bus_name` varchar(20) NOT NULL,
  `tot_seats` int(11) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `buses`
--

INSERT INTO `buses` (`id`, `bus_no`, `bus_name`, `tot_seats`, `username`, `password`) VALUES
(1, 'KL-41-N-9394', 'IV BISMI', 50, 'test', 'test'),
(2, 'test', 'test', 30, 'test', ''),
(3, 'abc', 'abc', 30, 'abc', ''),
(4, 'KL-43-H-5658', 'Express Kochi', 30, '356928093130740', '');

-- --------------------------------------------------------

--
-- Table structure for table `bus_data`
--

CREATE TABLE IF NOT EXISTS `bus_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bus_id` varchar(50) NOT NULL,
  `latitude` varchar(50) NOT NULL,
  `longitude` varchar(50) NOT NULL,
  `area` varchar(50) NOT NULL,
  `route_id` varchar(20) NOT NULL,
  `seats` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `bus_data`
--

INSERT INTO `bus_data` (`id`, `bus_id`, `latitude`, `longitude`, `area`, `route_id`, `seats`) VALUES
(1, '1', '9.944042386270874', '76.28342433532887', 'Willingdon Island', '11', '50'),
(2, '2', '10.04026609', '76.31624109', 'Edappally', '5', '50'),
(3, '3', '10.00917', '76.4528143', 'test run', '4', '50');

-- --------------------------------------------------------

--
-- Table structure for table `routes`
--

CREATE TABLE IF NOT EXISTS `routes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bus_id` varchar(20) NOT NULL,
  `src_location` varchar(20) NOT NULL,
  `dest_location` varchar(20) NOT NULL,
  `start_time` time NOT NULL,
  `arr_time` time NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `routes`
--

INSERT INTO `routes` (`id`, `bus_id`, `src_location`, `dest_location`, `start_time`, `arr_time`) VALUES
(1, '1', 'KOLENCHERY', 'ALUVA', '06:05:00', '07:10:00'),
(2, '2', 'KOLENCHERY', 'ALUVA', '08:35:00', '09:50:00'),
(3, '3', 'KOLENCHERY', 'ALUVA', '11:40:00', '12:45:00'),
(4, '4', 'KOLENCHERY', 'ALUVA', '14:15:00', '15:20:00'),
(5, '5', 'KOLENCHERY', 'ALUVA', '17:10:00', '18:15:00'),
(6, '1', 'ALUVA', 'KOLENCHERY', '07:15:00', '08:20:00'),
(7, '1', 'ALUVA', 'KOLENCHERY', '10:00:00', '11:05:00'),
(8, '1', 'ALUVA', 'KOLENCHERY', '12:55:00', '14:00:00'),
(9, '1', 'ALUVA', 'KOLENCHERY', '15:35:00', '16:40:00'),
(10, '1', 'ALUVA', 'KOLENCHERY', '18:23:00', '19:28:00'),
(11, '1', 'Fortkochi', 'Palarivattom', '09:30:00', '10:50:00');

-- --------------------------------------------------------

--
-- Table structure for table `stops`
--

CREATE TABLE IF NOT EXISTS `stops` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bus_id` varchar(20) NOT NULL,
  `route_id` int(11) NOT NULL,
  `stop_name` varchar(50) NOT NULL,
  `time` time NOT NULL,
  `latitude` varchar(50) NOT NULL,
  `longitude` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=103 ;

--
-- Dumping data for table `stops`
--

INSERT INTO `stops` (`id`, `bus_id`, `route_id`, `stop_name`, `time`, `latitude`, `longitude`) VALUES
(1, '1', 1, 'KOLENCHERY', '06:05:00', '9.981491', '76.473277'),
(2, '1', 1, 'KADAYIRUPPU', '06:10:00', '10.0014', '76.4583'),
(3, '1', 1, 'PAZHAMTHOTTAM', '06:20:00', '10.017007', '76.428494'),
(4, '1', 1, 'MORAKKALA', '06:30:00', '10.020613', '76.402159'),
(5, '1', 1, 'PALLIKKARA', '06:35:00', '10.025391', '76.403866'),
(6, '1', 1, 'KIZHAKKAMBALAM', '06:40:00', '10.036292', '76.408273'),
(7, '1', 1, 'PUKKATTUPADY', '06:50:00', '10.059208', '76.395444'),
(8, '1', 1, 'CHOONDY', '07:00:00', '10.084837', '76.371621'),
(9, '1', 1, 'ALUVA', '07:10:00', '10.108372', '76.351029'),
(10, '1', 2, 'KOLENCHERY', '08:35:00', '9.981491', '76.473277'),
(11, '1', 2, 'KADAYIRUPPU', '08:45:00', '10.0014', '76.4583'),
(12, '1', 2, 'PAZHAMTHOTTAM', '09:05:00', '10.017007', '76.428494'),
(13, '1', 2, 'MORAKKALA', '09:12:00', '10.020613', '76.402159'),
(14, '1', 2, 'PALLIKKARA', '09:16:00', '10.025391', '76.403866'),
(15, '1', 2, 'KIZHAKKAMBALAM', '09:20:00', '10.036292', '76.408273'),
(16, '1', 2, 'PUKKATTUPADY', '09:30:00', '10.059208', '76.395444'),
(17, '1', 2, 'CHOONDY', '10:45:00', '10.084837', '76.371621'),
(18, '1', 2, 'ALUVA', '11:10:00', '10.002452', '76.306276'),
(19, '1', 3, 'KOLENCHERY', '11:40:00', '9.981491', '76.473277'),
(20, '1', 3, 'KADAYIRUPPU', '11:45:00', '10.0014', '76.4583'),
(21, '1', 3, 'PAZHAMTHOTTAM', '11:55:00', '10.017007', '76.428494'),
(22, '1', 3, 'MORAKKALA', '12:05:00', '10.020613', '76.402159'),
(23, '1', 3, 'PALLIKKARA', '12:10:00', '10.025391', '76.403866'),
(24, '1', 3, 'KIZHAKKAMBALAM', '12:15:00', '10.036292', '76.408273'),
(25, '1', 3, 'PUKKATTUPADY', '12:25:00', '10.059208', '76.395444'),
(26, '1', 3, 'CHOONDY', '12:35:00', '10.084837', '76.371621'),
(27, '1', 3, 'ALUVA', '12:45:00', '10.108372', '76.351029'),
(28, '1', 4, 'KOLENCHERY', '14:15:00', '9.981491', '76.473277'),
(29, '1', 4, 'KADAYIRUPPU', '14:20:00', '10.0014', '76.4583'),
(30, '1', 4, 'PAZHAMTHOTTAM', '14:30:00', '10.017007', '76.428494'),
(31, '1', 4, 'MORAKKALA', '14:40:00', '10.020613', '76.402159'),
(32, '1', 4, 'PALLIKKARA', '14:45:00', '10.025391', '76.403866'),
(33, '1', 4, 'KIZHAKKAMBALAM', '14:50:00', '10.036292', '76.408273'),
(34, '1', 4, 'PUKKATTUPADY', '15:00:00', '10.059208', '76.395444'),
(35, '1', 4, 'CHOONDY', '15:10:00', '10.084837', '76.371621'),
(36, '1', 4, 'ALUVA', '15:20:00', '10.108372', '76.351029'),
(37, '1', 5, 'KOLENCHERY', '17:10:00', '9.981491', '76.473277'),
(38, '1', 5, 'KADAYIRUPPU', '17:15:00', '10.0014', '76.4583'),
(39, '1', 5, 'PAZHAMTHOTTAM', '17:25:00', '10.017007', '76.428494'),
(40, '1', 5, 'MORAKKALA', '17:35:00', '10.020613', '76.402159'),
(41, '1', 5, 'PALLIKKARA', '17:40:00', '10.025391', '76.403866'),
(42, '1', 5, 'KIZHAKKAMBALAM', '17:45:00', '10.036292', '76.408273'),
(43, '1', 5, 'PUKKATTUPADY', '17:55:00', '10.059208', '76.395444'),
(44, '1', 5, 'CHOONDY', '18:05:00', '10.084837', '76.371621'),
(45, '1', 5, 'ALUVA', '18:15:00', '10.108372', '76.351029'),
(46, '1', 6, 'ALUVA', '07:15:00', '10.108372', '76.351029'),
(47, '1', 6, 'CHOONDY', '07:25:00', '10.084837', '76.371621'),
(48, '1', 6, 'PUKKATTUPADY', '07:35:00', '10.059208', '76.395444'),
(49, '1', 6, 'KIZHAKKAMBALAM', '07:45:00', '10.036292', '76.408273'),
(50, '1', 6, 'PALLIKKARA', '07:50:00', '10.025391', '76.403866'),
(51, '1', 6, 'MORAKKALA', '07:55:00', '10.020613', '76.402159'),
(52, '1', 6, 'PAZHAMTHOTTAM', '08:05:00', '10.017007', '76.428494'),
(53, '1', 6, 'KADAYIRUPPU', '08:15:00', '10.0014', '76.4583'),
(54, '1', 6, 'KOLENCHERY', '08:20:00', '9.981491', '76.473277'),
(55, '1', 7, 'ALUVA', '10:00:00', '10.108372', '76.351029'),
(56, '1', 7, 'CHOONDY', '10:10:00', '10.084837', '76.371621'),
(57, '1', 7, 'PUKKATTUPADY', '10:20:00', '10.059208', '76.395444'),
(58, '1', 7, 'KIZHAKKAMBALAM', '10:30:00', '10.036292', '76.408273'),
(59, '1', 7, 'PALLIKKARA', '10:35:00', '10.025391', '76.403866'),
(60, '1', 7, 'MORAKKALA', '10:40:00', '10.020613', '76.402159'),
(61, '1', 7, 'PAZHAMTHOTTAM', '10:50:00', '10.017007', '76.428494'),
(62, '1', 7, 'KADAYIRUPPU', '11:00:00', '10.0014', '76.4583'),
(63, '1', 7, 'KOLENCHERY', '11:05:00', '9.981491', '76.473277'),
(64, '1', 8, 'ALUVA', '12:55:00', '10.108372', '76.351029'),
(65, '1', 8, 'CHOONDY', '13:05:00', '10.084837', '76.371621'),
(66, '1', 8, 'PUKKATTUPADY', '13:15:00', '10.059208', '76.395444'),
(67, '1', 8, 'KIZHAKKAMBALAM', '13:25:00', '10.036292', '76.408273'),
(68, '1', 8, 'PALLIKKARA', '13:30:00', '10.025391', '76.403866'),
(69, '1', 8, 'MORAKKALA', '13:35:00', '10.020613', '76.402159'),
(70, '1', 8, 'PAZHAMTHOTTAM', '13:45:00', '10.017007', '76.428494'),
(71, '1', 8, 'KADAYIRUPPU', '13:55:00', '10.0014', '76.4583'),
(72, '1', 8, 'KOLENCHERY', '14:00:00', '9.981491', '76.473277'),
(73, '1', 9, 'ALUVA', '15:35:00', '10.108372', '76.351029'),
(74, '1', 9, 'CHOONDY', '15:45:00', '10.084837', '76.371621'),
(75, '1', 9, 'PUKKATTUPADY', '15:55:00', '10.059208', '76.395444'),
(76, '1', 9, 'KIZHAKKAMBALAM', '16:05:00', '10.036292', '76.408273'),
(77, '1', 9, 'PALLIKKARA', '16:10:00', '10.025391', '76.403866'),
(78, '1', 9, 'MORAKKALA', '16:15:00', '10.025313', '76.402159'),
(79, '1', 9, 'PAZHAMTHOTTAM', '16:25:00', '10.017007', '76.428494'),
(80, '1', 9, 'KADAYIRUPPU', '16:35:00', '10.0014', '76.4583'),
(81, '1', 9, 'KOLENCHERY', '16:40:00', '9.981491', '76.473277'),
(82, '1', 10, 'ALUVA', '18:23:00', '10.108372', '76.351029'),
(83, '1', 10, 'CHOONDY', '18:33:00', '10.084837', '76.371621'),
(84, '1', 10, 'PUKKATTUPADY', '18:43:00', '10.059208', '76.395444'),
(85, '1', 10, 'KIZHAKKAMBALAM', '18:53:00', '10.036292', '76.408273'),
(86, '1', 10, 'PALLIKKARA', '18:58:00', '10.025391', '76.403866'),
(87, '1', 10, 'MORAKKALA', '19:03:00', '10.020613', '76.402159'),
(88, '1', 10, 'PAZHAMTHOTTAM', '19:13:00', '10.017007', '76.428494'),
(89, '1', 10, 'KADAYIRUPPU', '19:23:00', '10.0014', '76.4583'),
(90, '1', 10, 'KOLENCHERY', '19:28:00', '9.981491', '76.473277'),
(91, '1', 11, 'Fortkochi', '09:30:00', '9.968179', '76.244414'),
(92, '1', 11, 'Thamaraparambu', '09:40:00', '9.957350', '76.242447'),
(93, '1', 11, 'Veli', '09:45:00', '9.952338', '76.244688'),
(94, '1', 11, 'Thoppumpady', '09:55:00', '9.935302', '76.262658'),
(95, '1', 11, 'Naval Base', '10:05:00', '9.944392', '76.283747'),
(96, '1', 11, 'Thevara', '10:10:00', '9.949615', '76.291879'),
(97, '1', 11, 'Medical Trust', '10:20:00', '9.964293', '76.287389'),
(98, '1', 11, 'Kacheripady', '10:30:00', '9.985755', '76.281445'),
(99, '1', 11, 'Lissie JN', '10:35:00', '9.991062', '76.287796'),
(100, '1', 11, 'Kaloor JN', '10:40:00', '9.995056', '76.292173'),
(101, '1', 11, 'JLN Stadium', '10:45:00', '10.000795', '76.299605'),
(102, '1', 11, 'Palarivattom', '10:50:00', '10.004490', '76.305097');

-- --------------------------------------------------------

--
-- Table structure for table `transaction_tbl`
--

CREATE TABLE IF NOT EXISTS `transaction_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `wid` int(11) NOT NULL,
  `trans_num` varchar(200) NOT NULL,
  `bus_name` varchar(200) NOT NULL,
  `stop_name` varchar(200) NOT NULL,
  `amount` varchar(200) NOT NULL,
  `tdate` varchar(200) NOT NULL,
  `status` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `transaction_tbl`
--

INSERT INTO `transaction_tbl` (`id`, `uid`, `wid`, `trans_num`, `bus_name`, `stop_name`, `amount`, `tdate`, `status`) VALUES
(1, 1, 1, '3047027', '', '', '200', '07-04-2020', 'added'),
(2, 1, 1, '9065063', 'IV BISMI', 'KOLENCHERY', '78', '07-04-2020', 'deduct'),
(3, 1, 1, '9535003', 'IV BISMI', 'CHOONDY', '40', '07-04-2020', 'deduct'),
(4, 1, 1, '1659179', 'IV BISMI', 'CHOONDY', '8', '07-04-2020', 'deduct'),
(5, 1, 1, '8741241', 'IV BISMI', 'CHOONDY', '40', '07-04-2020', 'deduct'),
(6, 1, 1, '7670074', 'IV BISMI', 'CHOONDY', '16', '07-04-2020', 'deduct'),
(7, 1, 1, '9210906', '', '', '500', '08-04-2020', 'added'),
(8, 1, 1, '8743164', 'IV BISMI', 'Thamaraparambu', '33', '08-04-2020', 'deduct');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `name` varchar(255) NOT NULL,
  `Lname` varchar(200) NOT NULL,
  `phone_no` varchar(13) NOT NULL,
  `email` varchar(255) NOT NULL,
  `status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `name`, `Lname`, `phone_no`, `email`, `status`) VALUES
(1, 'abin', '123456', 'User', 'User', '7012656981', 'q', 'q'),
(6, 'duusdu', 'puzifd', 'xjxjdj', 'jxjx', 'uddi', 'hddu', ''),
(7, 'duusdu', '12345', 'xjxjdj', 'jxjx', '8157988437', 'hddu', ''),
(8, 'test', 'test', 'test', 'test', 'test', 'test', ''),
(9, 'test', 'test', 'test 1', 'test 1', '7012656981', 'abin.ck.9@gmail.com', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
