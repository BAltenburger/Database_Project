-- P4 

-- Create 3 Stored Procedures :- For your project create three stored procedures and emphasize how or why they would be used

-- Insert Event: Gather all of the input for the event details (event time, event venue, etc.) and insert it into the table
-- This is used to add events to the table


CREATE PROCEDURE AddEvent
    @event_id INT,
    @event_start DATETIME,
    @event_end DATETIME,
    @event_restriction BIT,
    @attendee_count INT
AS
BEGIN
    INSERT INTO Event (event_id, event_start, event_end, event_restriction, attendee_count)
    VALUES (@event_id, @event_start, @event_end, @event_restriction, @attendee_count);
END;
GO


-- Insert Attendees: Gather all of the input for the attendee details and insert it into the table
-- Used to add people to a guest list

CREATE PROCEDURE RegisterAttendee
    @event_id INT,
    @attendee_id INT
AS
BEGIN
    INSERT INTO Event_Attendence (event_id, attendee_id)
    VALUES (@event_id, @attendee_id);
END;
GO

-- Insert AddShift - This will be used when there needs to be another employee to be added to a shift
CREATE PROCEDURE AddShift
    @shift_id INT,
    @employee_id INT,
    @shift_start TIME,
    @shift_end TIME,
    @date DATE,
    @venue_id INT
AS
BEGIN
    INSERT INTO Shift (shift_id, employee_id, shift_start, shift_end, [date], venue_id)
    VALUES (@shift_id, @employee_id, @shift_start, @shift_end, @date, @venue_id);
END;
GO

 
--Views
--1. Event Details Overview
CREATE VIEW vw_EventDetails AS
SELECT
E.event_id,
E.event_start,
E.event_end,
C.contact_name,
C.contact_phone_number,
V.venue_name, 
V.street_address + " " + V.city + ", "+ V.state+ " " + V.zip AS venue_address,
E.attendee_count

FROM [Event] E
JOIN Contact C ON E.contact_id = C.contact_id
JOIN Venue V ON E.venue_id = V.venue_id;
GO

--2. Venue Event Overview
SELECT 
V.venue_id,
V.venue_name,
V.venue_address,
E.event_id,
E.event_start,
E.event_end

FROM Venue V
--i hardcoded this?? not sure if we can take venue_id as a param
WHERE V.venue_id = 1
JOIN Employee E ON E.venue_id = V.venue_id;
GO

--3. Manager Shift Summary 
CREATE VIEW vw_EmployeeShiftSummary AS
SELECT 
E.employee_id,
E.employee_name, 
E.employee_position,
--will be manager's ID
S.shift_id, 
S.shift_start,
S.shift_end,
S.date, 
V.venue_name

FROM Employee E 
JOIN Shift S ON E.employee_id = S.employee_id
JOIN Venue V ON S.venue_id = V.venue_id; 
GO

 -- Implement 1 Column Encryption :- For any 1 column in your table, implement the column encryption for security purposes
-- Encrypt the email of the contact

CREATE MASTER KEY ENCRYPTION BY PASSWORD = 'D@t@bases4Ev3r1Swear!';

CREATE SYMMETRIC KEY EmailEncryptionKey
WITH ALGORITHM = AES_256
ENCRYPTION BY PASSWORD = 'D@t@bases4Ev3r1Swear!';
GO

-- Open the symmetric key with the password
OPEN SYMMETRIC KEY EmailEncryptionKey
DECRYPTION BY PASSWORD = 'D@t@bases4Ev3r1Swear!';

-- Encrypt existing email data in the Contact table
UPDATE Contact
SET email = EncryptByKey(Key_GUID('EmailEncryptionKey'), email);

-- Close the symmetric key
CLOSE SYMMETRIC KEY EmailEncryptionKey;
GO
 
 
--Indexes
-- 1. Event_Attendence with index on attendee_id 
CREATE NONCLUSTERED INDEX idx_attendee_id ON Event_Attendence(attendee_id);

--2.  Shift based on employee_id
CREATE NONCLUSTERED INDEX idx_employee_id ON Shift(employee_id);

 --3. Venue based on capacity
CREATE NONCLUSTERED INDEX idx_total_capacity ON Venue(total_capacity);

