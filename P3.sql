/*
Team members:
- Betsy Altenburger: hjc6uh
- Michelle Tran: txf5ek
- Isabelle Engel: ngs5tf
*/

CREATE DATABASE Bar;
GO
Use Bar;
GO

--SECTION 1: TABLE AND SCHEMA IDENTIFICATION

--Event(EventID, EventStart, EventEnd, EventRestriction, AttendeeCount) 
CREATE TABLE Event(
    event_id INT PRIMARY KEY, 
    --datetime format: YYYY-MM-DD HH-MM-SSS
    event_start DATETIME,
    event_end DATETIME,
    --use following as boolean: 0=false, 1=true
    event_restriction BIT,
    attendee_count INT
);

--Venue(VenueID, VenueName, VenueWebsite, TotalCapacity, StreetAddress, City, State, Zip) 
CREATE TABLE Venue(
    venue_id INT PRIMARY KEY,
    venue_name VARCHAR(50),
    venue_website VARCHAR(50),
    total_capacity INT,
    street_address VARCHAR(100),
    city VARCHAR(30),
    [state] VARCHAR(5),
    zip INT
);

CREATE TABLE Employee(
    employee_id INT NOT NULL PRIMARY KEY,
    employee_name VARCHAR(75),
    is_manager BIT
);

CREATE TABLE Attendee(
    attendee_id INT NOT NULL PRIMARY KEY,
    attendee_name VARCHAR(50),
    attendee_phone_number VARCHAR(18) NOT NULL,
    CHECK (attendee_phone_number LIKE '___-___-____')
);

CREATE TABLE Shift(
    shift_id INT NOT NULL PRIMARY KEY,
    employee_id INT,
    shift_start TIME,
    shift_end TIME,
    [date] DATE,
    venue_id INT,
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id),
    
);

CREATE TABLE Tab(
    tab_id INT NOT NULL PRIMARY KEY,
    is_open BIT, 
    tab_amount INT,
    money_spent INT,
    signature_drink VARCHAR(100),
    CONSTRAINT tab_amount_CHK CHECK (tab_amount >= 100),
    CONSTRAINT tab_amount_CHK1 CHECK (tab_amount >= money_spent)

);

CREATE TABLE Contact
(
    contact_id INT PRIMARY KEY,
    contact_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(18) NOT NULL,
    CHECK (phone_number LIKE '___-___-____')    
);

CREATE TABLE Reservation
(
    event_id INT PRIMARY KEY,
    employee_id INT NOT NULL,
    time_approved TIME,
    tab_id INT NOT NULL,
    venue_id INT NOT NULL,
    FOREIGN KEY (event_id) REFERENCES Reservation(event_id),
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id),
    FOREIGN KEY (tab_id) REFERENCES Tab(tab_id),
    FOREIGN KEY (venue_id) REFERENCES Venue(venue_id)
);

CREATE TABLE Request
(
   event_id INT PRIMARY KEY,
   [status] VARCHAR(50) NOT NULL,
   FOREIGN KEY (event_id) REFERENCES Reservation(event_id)
);

CREATE TABLE EventExpense
(
    event_expense_id INT PRIMARY KEY,
    event_id INT NOT NULL,
    Cost DECIMAL(10, 2) NOT NULL,
    Paid DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (event_id) REFERENCES Reservation(event_id),
    CHECK (Paid <= Cost)
);

--Event_Attendence(AttendeeID, EventID)
CREATE TABLE Event_Attendence(
    event_id INT NOT NULL,
    attendee_id INT NOT NULL,
    PRIMARY KEY (event_id, attendee_id)
);

--Event_Employee(EventID, EmployeeID)
CREATE TABLE Event_Employee(
    event_id INT NOT NULL,
    employee_id INT NOT NULL,
    PRIMARY KEY (event_id, employee_id),
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
);

--Event_Contact(EventID, ContactID)
CREATE TABLE Event_Contact(
    event_id INT NOT NULL,
    contact_id INT NOT NULL,
    PRIMARY KEY (event_id, contact_id)
);


CREATE TABLE Employee_Shift(
    employee_id INT NOT NULL,
    shift_id INT NOT NULL,
    CONSTRAINT Empolyee_Shift_PK PRIMARY KEY (employee_id, shift_id)
);


--SECTION 2: INSERTING INTO TABLES

INSERT INTO Attendee(attendee_id, attendee_name, attendee_phone_number) VALUES
(1, 'Jim Ryan','434-456-7890'),
(2, 'Stephanie Engel', '987-654-3210'),
(3, 'Harry Styles', '555-555-5555'),
(4, 'Sabrina Carpenter', '445-425-2575'),
(5, 'Thomas Jefferson', '434-431-4324'),
(6, 'Naomi Tran',  '124-645-3765'),
(7, 'John Doe',  '353-355-2353'),
(8, 'Jane Smith', '367-765-6545'),
(9, 'Sam Wilson',  '525-463-3534'),
(10, 'Chris Brown', '564-643-7653'),
(11, 'Patricia Le',  '765-456-3545'),
(12, 'David Lee',  '464-346-3543'),
(13, 'Taylor Swift' , '325-243-4235'),
(14, 'Sophia Martinez',  '567-765-7567'),
(15, 'Liam Harris',  '756-576-4653'),
(16, 'Emily Walker',  '345-634-7565'),
(17, 'Lucas Scott',  '345-768-7453'),
(18, 'Phoebe King',  '345-756-8764'),
(19, 'Amelia Earheart', '876-678-5764'),
(20, 'Michael Scott','465-687-5646'),
(21, 'Tolkien Black', '571-432-2342'),
(22, 'Heidi Turner', '434-423-2346'),
(23, 'Stan Marsh',  '571-940-5438'),
(24, 'Kyle Broflovski',  '703-965-6453'),
(25, 'Kenny McCornmick', '571-534-5743'),
(26, 'Eric Cartman', '785-458-1249'),
(27, 'Winston Bishop',  '523-987-6736'),
(28, 'Nick Miller',  '968-756-2347'),
(29, 'Jessica Day', '876-654-8762'),
(30, 'Winston Schmidt',  '876-342-4367');

INSERT INTO Shift (shift_id, employee_id, shift_start, shift_end, [date], venue_id) VALUES
(1, 2, '09:00:00', '12:00:00', '2024-10-14', 1),
(2, 3, '10:00:00', '00:00:00', '2023-10-14', 1),
(3, 12, '19:00:00', '22:00:00', '2024-12-25', 30),
(4, 2, '12:00:00', '00:00:00', '2024-11-01', 10),
(5, 4, '20:00:00', '22:00:00', '2024-12-10', 15),
(6, 10, '10:30:00', '13:30:00', '2024-11-01', 20),
(7, 22, '05:45:00', '12:00:00', '2025-05-18', 20),
(8, 19, '10:00:00', '13:00:00', '2025-01-05', 20),
(9, 2, '13:00:00', '15:30:00', '2024-10-31', 19),
(10, 30, '10:00:00', '00:00:00', '2024-10-20', 15),
(11, 17, '09:00:00', '12:00:00', '2024-10-21', 13), 
(12, 23, '10:00:00', '00:00:00', '2024-10-21', 28),
(13, 20, '09:00:00', '12:00:00', '2024-10-21', 29),
(14, 30, '10:00:00', '00:00:00', '2024-10-27', 10),
(15, 24, '09:00:00', '12:00:00', '2024-10-29', 16),
(16, 21, '10:00:00', '00:00:00', '2024-10-20', 19),
(17, 26, '09:00:00', '12:00:00', '2024-11-20', 18),
(18, 10, '10:00:00', '00:00:00', '2024-11-06', 13),
(19, 9, '09:00:00', '12:00:00', '2024-10-14', 7),
(20, 13, '10:00:00', '00:00:00', '2024-11-17', 10),
(21, 13, '09:00:00', '12:00:00', '2024-11-24', 9),
(22, 12, '10:00:00', '00:00:00', '2024-12-24', 8),
(23, 19, '09:00:00', '12:00:00', '2024-12-07', 7),
(24, 25, '10:00:00', '00:00:00', '2024-10-30', 6),
(25, 28, '09:00:00', '12:00:00', '2025-01-20', 5),
(26, 29, '10:00:00', '00:00:00', '2024-11-13', 4),
(27, 30, '09:00:00', '12:00:00', '2024-11-24', 3),
(28, 24, '10:00:00', '00:00:00', '2024-10-18', 2),
(29, 26, '09:00:00', '12:00:00', '2024-10-30', 1),
(30, 30, '10:00:00', '00:00:00', '2024-10-31', 10);

INSERT INTO Event(event_id, event_start, event_end, event_restriction, attendee_count) VALUES
(1, '2024-01-01 7:00', '2024-01-01 10:00', 1, 100), 
(2, '2024-01-02 14:00', '2024-01-02 17:30', 1, 50),
(3, '2024-01-03 20:00', '2024-01-03 22:00', 1, 300), 
(4, '2024-01-04 17:00', '2024-01-04 19:30', 1, 75),
(5, '2024-01-05 10:00', '2024-01-05 12:00', 0, 200), 
(6, '2024-01-06 14:00', '2024-01-06 17:30', 0, 50),
(7, '2024-01-07 1:00', '2024-01-07 3:00', 1, 35), 
(8, '2024-01-08 5:00', '2024-01-08 9:30', 1, 350),
(9, '2024-01-09 4:00', '2024-01-09 6:00', 0, 100), 
(10, '2024-01-10 14:00', '2024-01-10 15:30', 1, 100),
(11, '2024-01-11 4:00', '2024-01-11 6:00', 1, 50), 
(12, '2024-01-12 14:00', '2024-01-12 15:30', 1, 150),
(13, '2024-01-13 8:30', '2024-01-13 11:00', 0, 100), 
(14, '2024-01-14 22:00', '2024-01-14 23:45', 0, 250),
(15, '2024-01-15 14:00', '2024-01-15 18:00', 1, 300), 
(16, '2024-01-16 21:00', '2024-01-16 23:00', 0, 100),
(17, '2024-01-17 9:00', '2024-01-17 13:00', 1, 150), 
(18, '2024-01-18 4:00', '2024-01-18 6:00', 0, 50),
(19, '2024-01-19 10:00', '2024-01-19 13:00', 1, 75), 
(20, '2024-01-20 18:00', '2024-01-20 21:00', 1, 200),
(21, '2024-01-21 22:00', '2024-01-21 23:30', 1, 80), 
(22, '2024-01-22 21:00', '2024-01-22 23:00', 0, 125),
(23, '2024-01-23 10:00', '2024-01-23 12:30', 1, 25), 
(24, '2024-01-24 21:00', '2024-01-24 23:00', 1, 125),
(25, '2024-01-25 12:00', '2024-01-25 12:30', 0, 150), 
(26, '2024-01-26 14:00', '2024-01-26 16:00', 0, 75),
(27, '2024-01-27 10:00', '2024-01-27 12:00', 0, 100), 
(28, '2024-01-28 21:00', '2024-01-28 23:00', 0, 125),
(29, '2024-01-29 15:00', '2024-01-29 18:00', 0, 50), 
(30, '2024-01-30 21:00', '2024-01-30 23:00', 1, 300);

--Venue table
INSERT INTO Venue(venue_id, venue_name, venue_website, total_capacity, street_address, city, [state], zip) VALUES 
(1, 'Starry Meadow', 'www.starrymeadow.com', 400, '300 Meadow Lane', 'Richmond', 'VA', 23226),
(2, 'Castle on the Hill', 'www.castlehill.com', 150, '12 Grove Drive', 'Nellysford', 'VA', 22990),
(3, 'The Rotunda', 'www.virginia.edu', 200, '30 University Drive', 'Charlottesville', 'VA', 22903),
(4, 'Peaceful Barn', 'www.barninpeace.com', 350, '100 Gravel Way', 'Archdale', 'NC', 28800),
(5, 'Oceanside Villa', 'www.bythesea.com', 150, '8 Ocean Drive', 'Virginia Beach', 'VA', 23231),
(6, 'River Mansion', 'www.onthejames.com', 200, '3390 Witon Drive', 'Richmond', 'VA', 23330), 
(7, 'Capitol Perch', 'www.capitolperch.com', 250, '15 Constitution Drive', 'Washington', 'DC', 20000),
(8, 'Sea Biscuit Ranch', 'www.ranchinky.com', 500, '1 Ranch Road', 'Louisville', 'KY', 30401),
(9, 'Wooded Escape', 'www.intothewoods.net', 75, 'N/A', 'Shennandoah', 'VA', 24990),
(10, 'Chem 402', 'www.virginia.edu/chemistry', 275, '400 McCormick Drive', 'Charlottesville', 'VA', 22903),
(11, 'Trinity Irish Pub', 'www.trinityonthecorner.com', 600, '120 University Ave', 'Charlottesville', 'VA', 22903), 
(12, 'Capital One Arena', 'www.caponearena.com', 20000, '4000 F Street', 'Washington', 'DC', 20054),
(13, 'Clarendon Ballroom', 'www.clarendonballroom.com', 1000, '300 Fairfax Drive', 'Arlington', 'VA', 21004),
(14, 'Moonlight Lake Village', 'www.lakebythemoon.com', 400, '55 Lake Drive', 'Clemson', 'SC', 34002),
(15, 'Cat Jacks Taproom', 'www.catjack.com', 150, '625 Carolina Drive', 'Raleigh', 'NC', 28905), 
(16, 'Mastercard Stadium', 'www.mcstadium.com', 80000, '12 Mastercard Way', 'Arlington', 'VA', 21067),
(17, 'Stephanies Aquarium', 'www.stephaniesaquarium.com', 80, '172 Aquarium Drive', 'Duck', 'NC', 26789),
(18, 'Marathon Music Works', 'www.marathonmusic.com', 1500, '1900 Broadway Road', 'Nashville', 'TN', 49880), 
(19, 'Jefferson Theater','www.jefftheater.com', 1500, '1200 Downtown Mall', 'Charlottesville', 'VA', 22910 ),
(20, 'American Airlines Arena', 'aaarena.com', 65000, '1500 Seaside Drive', 'Miami', 'FL', 48900),
(21, 'Filmore', 'www.filmore.com', 5000, '27 Ocean Avenue', 'Miami', 'FL', 48906),
(22, 'The Ampitheater', 'www.virginia.edu/ampitheater', 2000, '58  McCormick Drive', 'Charlottesville', 'VA', 22903),
(23, 'KDouse', 'www.kappadelta.org', 50, '132 Chancellor Street', 'Charlottesville', 'VA', 22903),
(24, 'The Well', 'N/A', 12, 'Secret Underground', 'Hot Springs', 'VA', 24900),
(25, 'Boylan Heights', 'www.boylanheights.com', 500, '240 University Ave','Charlottesville', 'VA', 22903),
(26, 'Coupe Devilles Garden Terrace', 'www.coupes.com', 425, '12 Elliewood Ave', 'Charlottesville', 'VA', 22903), 
(27, 'Ellies Country Club', 'www.elliescville.com', 780, '14 Elliewood Ave', 'Charlottesville', 'VA', 22903),
(28, 'The Whiskey Jar', 'www.thewhiskeyjarva.com', 100, '4 Downtown Mall', 'Charlottesville', 'VA', 22910),
(29, 'The Fitzroy', 'www.fitzroy.com', 75, '10 Downtown Mall', 'Charlottesville', 'VA', 22910),
(30, 'OK Energy House', 'www.okenergy.com/house', 750, '120  Rugby Road', 'Charlottesville', 'VA', 22903);

--Event Attendence table
INSERT INTO Event_Attendence(event_id, attendee_id) VALUES 
(1, 1),
(1, 2),
(1, 3),
(2, 3),
(3, 4),
(4, 5),
(4, 6),
(4, 7),
(4, 8), 
(4, 9), 
(4, 10),
(4, 11),
(5, 11), 
(6, 11), 
(7, 12),
(7, 13), 
(8, 14), 
(9, 15), 
(10, 11),
(11, 11),
(12, 20), 
(13, 20), 
(14, 21),
(15, 22),
(16, 23),
(17, 24), 
(18, 25),
(19, 26),
(20, 27),
(21, 28), 
(22, 29),
(23, 30); 

--Event Employee table: maps all employees working every event
INSERT INTO Event_Employee(event_id, employee_id) VALUES 
(11, 23),
(11, 16),
(16, 11),
(2, 13),
(17, 9),
(24, 22),
(1, 21),
(30, 16),
(4, 3),
(18, 13),
(8, 5),
(1, 16),
(7, 23),
(17, 8),
(5, 10),
(16, 24),
(19, 3),
(4, 29),
(14, 22),
(8, 11),
(10, 9),
(20, 26),
(3, 23),
(12, 7),
(25, 27),
(4, 6),
(8, 1),
(17, 21),
(7, 28),
(22, 2),
(18, 19),
(30, 28),
(4, 14),
(21, 22),
(9, 4);

--Event_Contact table
INSERT INTO Event_Contact(event_id, contact_id) VALUES 
(15, 26),
(1, 26),
(13, 16),
(4, 29),
(7, 11),
(28, 24),
(14, 2),
(8, 4),
(12, 11),
(17, 15),
(29, 27),
(19, 4),
(11, 18),
(10, 7),
(11, 14),
(27, 15),
(9, 23),
(17, 2),
(5, 9),
(6, 18),
(22, 8),
(8, 7),
(22, 11),
(28, 17),
(26, 26),
(1, 29),
(28, 18),
(6, 25),
(10, 26),
(8, 20),
(25, 22),
(24, 1),
(20, 9),
(13, 15),
(24, 25);


INSERT INTO Employee(employee_id, employee_name, is_manager) VALUES
(1, 'Betsy Altenburger', 0),
(2, 'Liam Altenburger', 0),
(3, 'Matthew Altenburger', 1),
(4, 'Isabelle Engel', 0),
(5, 'Michelle Tran', 1),
(6, 'Jim Ryan', 1),
(7, 'Jane Doe', 1),
(8, 'Ben', 0),
(9, 'Carl', 0),
(10, 'Rory Gilmore', 0),
(11, 'Lorelai Gilmore', 1),
(12, 'Emily Gilmore', 0),
(13, 'Richard Gilmore', 0),
(14, 'Michael Scott', 0),
(15, 'Sign Guy', 0),
(16, 'Jack', 0),
(17, 'Sabrina Carpenter', 0),
(18, 'Taylor Swift', 0),
(19, 'Travis Kelce', 0),
(20, 'Ella', 0),
(21, 'Emily', 1),
(22, 'Kate', 1),
(23, 'Margot', 0),
(24, 'Stanley', 1),
(25, 'Katherine', 0),
(26, 'Julia', 0),
(27, 'Chiara', 1),
(28, 'Kamala Harris', 0),
(29, 'Julian', 0),
(30, 'Hudson', 1);

INSERT INTO Employee_Shift(shift_id, employee_id) VALUES
(1, 2),
(2, 3),
(3, 12),
(4, 2),
(5, 4),
(6, 10),
(7, 22),
(8, 19),
(9, 2),
(10, 30),
(11, 17), 
(12, 23),
(13, 20),
(14, 30),
(15, 24),
(16, 21),
(17, 26),
(18, 10),
(19, 9),
(20, 13),
(21, 13),
(22, 12),
(23, 19),
(24, 25),
(25, 28),
(26, 29),
(27, 30),
(28, 24),
(29, 26),
(30, 30);

INSERT INTO Tab(tab_id, is_open, tab_amount, money_spent, signature_drink) VALUES
(1,0,1000,0, 'Bold Rock'),
(2,1,101,100, 'Whiskey Ginger'),
(3,1,2000,1000, 'Vokda Cranberry'),
(4,1,1500,572, 'Ginger Sour'),
(5,1,1234,0, 'Ranch Water'),
(6,0,300,100, 'Tequlia Sunrise'),
(7,0,400,300, 'Cosmo'),
(8,1,500,500, 'Vodka Soda'),
(9,0,10000,100, 'Mimosa'),
(10,0,20000,200, 'Bloody Mary'),
(11,0,100,50, 'Beer'),
(12,1,1000,0, 'White Claw'),
(13,1,1000,0, 'Bold Rock'),
(14,1,100,100, 'Margarita'),
(15,1,1000,0, 'Martini with a Twist'),
(16,0,500,0, 'Espresso Martini'),
(17,0,500,0, 'Moroccan Mint'),
(18,1,600,0, 'Old Fashioned'),
(19,0,1033500, 1295, 'Sangria'),
(20,0,10000,1035, 'Aperol Spritz'),
(21,0,3000,243, 'Bourban Lemonade'),
(22,1,34234,100, 'Manhattan'),
(23,1,1000,300, 'Mai Tai the Know'),
(24,1,1500,50, 'Marry Me Mule'),
(25,1,343,30, 'Martmony Margarite'),
(26,0,50000,555, 'Wine'),
(27,0,1243,100, 'Margarita on the Rocks'),
(28,1,13500,100, 'Whiskey Sour'),
(29,0,1000,0, 'Dirty Martini'),
(30,0,1000,0, 'Lychee Martini');

INSERT INTO Contact (contact_id, contact_name, email, phone_number) VALUES
(1, 'Jim Ryan', 'jimryan@virginia.edu', '434-456-7890'),
(2, 'Stephanie Engel', 'stephanie@seaworld.com', '987-654-3210'),
(3, 'Harry Styles', 'harrystyles@gmail.com', '555-555-5555'),
(4, 'Sabrina Carpenter', 'sabrina@sb.com', '445-425-2575'),
(5, 'Thomas Jefferson', 'tj@virginia.edu', '434-431-4324'),
(6, 'Naomi Tran', 'naomi@seaworld.com', '124-645-3765'),
(7, 'John Doe', 'johndoe@hotmail.com', '353-355-2353'),
(8, 'Jane Smith', 'janesmith@hotmail.com', '367-765-6545'),
(9, 'Sam Wilson', 'samwilson@gmail.com', '525-463-3534'),
(10, 'Chris Brown', 'breezy420@gmail.com', '564-643-7653'),
(11, 'Patricia Le', 'patty4@gmail.com', '765-456-3545'),
(12, 'David Lee', 'davidlee@yahoo.com', '464-346-3543'),
(13, 'Taylor Swift', 'taylorswift@gmail.com', '325-243-4235'),
(14, 'Sophia Martinez', 'sophia435@yahoo.com', '567-765-7567'),
(15, 'Liam Harris', 'liamharris@aol.com', '756-576-4653'),
(16, 'Emily Walker', 'cutedog123@gmail.com', '345-634-7565'),
(17, 'Lucas Scott', 'lucasscott@aol.com', '345-768-7453'),
(18, 'Phoebe King', 'phoebeking@yahoo.com', '345-756-8764'),
(19, 'Amelia Earheart', 'boygeniusfan@gmail.com', '876-678-5764'),
(20, 'Michael Scott', 'michaelsc0tt@gmail.com', '465-687-5646'),
(21, 'Tolkien Black', 'tolkienblack@yahoo.com', '571-432-2342'),
(22, 'Heidi Turner', 'heiditurner@aol.com', '434-423-2346'),
(23, 'Stan Marsh', 'stanmarsh@aol.com', '571-940-5438'),
(24, 'Kyle Broflovski', 'kylebroflovski@gmail.com', '703-965-6453'),
(25, 'Kenny McCornmick', 'kennymccormick@aol.com', '571-534-5743'),
(26, 'Eric Cartman', 'ericcartman@yahoo.com', '785-458-1249'),
(27, 'Winston Bishop', 'winstonbishop@aol.com', '523-987-6736'),
(28, 'Nick Miller', 'nickmiller@aol.com', '968-756-2347'),
(29, 'Jessica Day', 'jessicaday@gmail.com', '876-654-8762'),
(30, 'Winston Schmidt', 'winstonschmidt@gmail.com', '876-342-4367');

INSERT INTO Reservation (event_id, employee_id, time_approved, tab_id, venue_id) VALUES
(1, 1, '10:30AM', 1, 1),
(2, 2, NULL, 2, 2),
(3, 3, '02:15PM', 3, 3),
(4, 4, NULL, 4, 4),
(5, 5, '04:45PM', 5, 5),
(6, 6, NULL, 6, 6),
(7, 7, '09:00AM', 7, 7),
(8, 8, NULL, 8, 8),
(9, 9, '11:30AM', 9, 9),
(10, 10, NULL, 10, 10),
(11, 11, '03:00PM', 11, 11),
(12, 12, NULL, 12, 12),
(13, 13, '06:15PM', 13, 13),
(14, 14, NULL, 14, 14),
(15, 15, '08:00PM', 15, 15),
(16, 16, NULL, 16, 16),
(17, 17, '01:45PM', 17, 17),
(18, 18, NULL, 18, 18),
(19, 19, '07:00PM', 19, 19),
(20, 20, NULL, 20, 20),
(21, 21, '05:30PM', 21, 21),
(22, 22, NULL, 22, 22),
(23, 23, '09:15AM', 23, 23),
(24, 24, NULL, 24, 24),
(25, 25, '10:45AM', 25, 25),
(26, 26, NULL, 26, 26),
(27, 27, '03:30PM', 27, 27),
(28, 28, NULL, 28, 28),
(29, 29, '12:00PM', 29, 29),
(30, 30, NULL, 30, 30);


INSERT INTO Request (event_id, [status]) VALUES
(1, 'Approved'),
(2, 'Pending'),
(3, 'Approved'),
(4, 'Pending'),
(5, 'Approved'),
(6, 'Pending'),
(7, 'Approved'),
(8, 'Pending'),
(9, 'Approved'),
(10, 'Pending'),
(11, 'Approved'),
(12, 'Pending'),
(13, 'Approved'),
(14, 'Pending'),
(15, 'Approved'),
(16, 'Pending'),
(17, 'Approved'),
(18, 'Pending'),
(19, 'Approved'),
(20, 'Pending'),
(21, 'Approved'),
(22, 'Pending'),
(23, 'Approved'),
(24, 'Pending'),
(25, 'Approved'),
(26, 'Pending'),
(27, 'Approved'),
(28, 'Pending'),
(29, 'Approved'),
(30, 'Pending');

INSERT INTO EventExpense (event_expense_id, event_id, cost, paid) VALUES
(1, 1, 500.00, 250.00),
(2, 2, 1200.00, 800.00),
(3, 3, 1500.00, 1200.00),
(4, 4, 700.00, 400.00),
(5, 5, 1800.00, 1500.00),
(6, 6, 2300.00, 1000.00),
(7, 7, 3200.00, 2900.00),
(8, 8, 2900.00, 2300.00),
(9, 9, 6000.00, 4500.00),
(10, 10, 7500.00, 3420.00),
(11, 11, 800.00, 600.00),
(12, 12, 4000.00, 3200.00),
(13, 13, 7800.00, 5600.00),
(14, 14, 5200.00, 4500.00),
(15, 15, 650.00, 350.00),
(16, 16, 7400.00, 643.00),
(17, 17, 3000.00, 1500.00),
(18, 18, 6000.00, 5800.00),
(19, 19, 5000.00, 4500.00),
(20, 20, 1200.00, 850.00),
(21, 21, 6800.00, 4300.00),
(22, 22, 7300.00, 7000.00),
(23, 23, 1400.00, 1000.00),
(24, 24, 2400.00, 2200.00),
(25, 25, 3100.00, 1600.00),
(26, 26, 7600.00, 5000.00),
(27, 27, 4500.00, 3400.00),
(28, 28, 5900.00, 4900.00),
(29, 29, 3400.00, 3000.00),
(30, 30, 5000.00, 2500.00);

--SECTION 3: QUERIES 
--1 .EVENTID JOIN with Request

--2. EVENTID JOIN with Reservation

--3. Aggregate: COUNT(ATTENDEES) FOR AN EVENT 

--4. Aggregate MAXIMUM TAB AMOUNT FOR OPEN TAB 

--5. Aggregate: AVERAGE TAB AMOUNT FOR OPEN TAB 

--6. Subquery: Find events where the expense paid is less than the cost (aka still have stuff to pay off)

--7. Subquery: Make sure every event has X number of employees working 

--8. Subquery/join: make sure every event has at least one manager working 

--9. Aggregate: employees who have worked at multiple venues 

--10. Join: events reservation and event times do not match


