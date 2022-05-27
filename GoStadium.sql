CREATE TABLE `FAN` (
  `FanId` int(4) PRIMARY KEY,
  `FirstName` varchar(256) NOT NULL,
  `LastName` varchar(256) NOT NULL,
  `UserName` varchar(256) NOT NULL,
  `Paycard` int(20) NOT NULL,
  `Email` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `BOOKING` (
  `SeatNumber` int(6) PRIMARY KEY,
  `Side` varchar(256) NOT NULL,
  `Price` double NOT NULL,
  `BookingDate` date NOT NULL,
  `SeatId` int(4) NOT NULL,
  `FanId` int(4) NOT NULL,
  `GameId` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `SEAT` (
  `SeatId` int(6) NOT NULL PRIMARY KEY,
  `SeatNumber` int NOT NULL,
  `SideId` int NOT NULL
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `SIDE` (
  `SideId` int(6) NOT NULL PRIMARY KEY,
  `SideName` varchar(256) NOT NULL,
  `Price` int NOT NULL,
  `MaxSeats` int NOT NULL,
  `MinSeats` int NOT NULL
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `GAME` (
  `GameId` int(10) NOT NULL PRIMARY KEY,
  `GameDate` date NOT NULL,
  `GameHour` time NOT NULL,
  `TeamName` varchar(256) NOT NULL,
  `Competition` varchar(256) NOT NULL
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `FAN` (`FanId`, `FirstName`, `LastName`, `UserName`, `Paycard`, `Email`) VALUES
(1011, 'Paco', 'Leon', 'pleon',1001,'pleon@gmail.com'),
(1022, 'Mario','Kart','mkart',1002,'mkart@gmail.com'),
(1033, 'Maria','Rosa', 'mrosa',1003,'mrosa@gmail.com'),
(1044, 'David','Villa', 'dvilla',1004,'dvilla@gmail.com');


INSERT INTO `SEAT` (`SeatId`, `SeatNumber`, `SideId`) VALUES
(1111, 1, 1100),
(2222, 2,1100),
(3333, 3,2200),
(4444, 4,2200),
(5555, 5, 3300),
(6666, 6,3300),
(7777, 7,4400),
(8888, 8,4400);

INSERT INTO `SIDE` (`SideId`, `SideName`, `Price`, `MaxSeats`, `MinSeats`) VALUES
(1100, 'VIP', 200, 2,1),
(2200, 'Lateral', 80, 2,1),
(3300, 'Gol', 60, 2,1),
(4400, 'Córner', 50, 2,1);

INSERT INTO `GAME` (`GameId`,`GameDate`,`GameHour`,`TeamName`,`Competition`) VALUES
(1111,'2022-06-12','21:00:00','Barcelona','LaLiga'),
(2222,'2022-05-19','20:00:00','Madrid','ChampionsLeague');

ALTER TABLE BOOKING
ADD CONSTRAINT FK_Bookin2 FOREIGN KEY (FanId)
REFERENCES FAN(FanId);

ALTER TABLE BOOKING
ADD CONSTRAINT FK_Booking1 FOREIGN KEY (SeatId)
REFERENCES SEAT(SeatId);

ALTER TABLE BOOKING
ADD CONSTRAINT FK_Booking3 FOREIGN KEY (GameId)
REFERENCES GAME(GameId);

ALTER TABLE SEAT
ADD CONSTRAINT FK_Seat1 FOREIGN KEY (SideId)
REFERENCES SIDE(SideId);
