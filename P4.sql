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



-- Functions
-- 1. Calculate Remaining Tab
-- To calculate MoneyLeft in a tab (by subtracting MoneySpent from TabAmount) could be used in various queries to see how much is left on the bar tab at any point (returns amount remaining)
-- This is good because it can be helpful for the bar to let the guests know how much is left. The guest might also want to know how much is left to see if they should get another drink before it runs out
CREATE FUNCTION  CalculateRemainingTab(
    @tab_id INT
)
    RETURNS INT
    AS
    BEGIN
        DECLARE @remaining_tab INT;
        SELECT @remaining_tab = tab_amount - money_spent
        FROM Tab
        WHERE tab_id = @tab_id;
        RETURN @remaining_tab;
END;


-- 2. Get Attendees Count for Event
-- Returns the count of attendees for a specific event by joining Event_Attendence and Attendee tables 
-- This is good for the security to know how many people should be there. It is also helpful to make sure the event does not go over the venues capacity
CREATE FUNCTION  GetAttendeeCount(
    @tab_id INT
)
    RETURNS INT
    AS
    BEGIN
        DECLARE @remaining_tab INT;
        SELECT @remaining_tab = tab_amount - money_spent
        FROM Tab
        WHERE tab_id = @tab_id;
        RETURN @remaining_tab;
END;

-- 3. Generate Event Expense Report
-- A procedure that aggregates expenses for an event, summarizing costs
-- This would be helpful for accounting / financial purposes. The contact who is paying for the event needs this information to know how much to pay.
-- The venue and the bar also need this information to know how much they are owed.
CREATE FUNCTION  GetAttendeeCount(
    @event_id INT
)
    RETURNS INT
    AS
    BEGIN
        DECLARE @total_cost INT;
        SELECT @total_cost = cost + tab_amount
        FROM EventExpense
        JOIN Tab ON event_id = event_id
        WHERE event_id = @event_id;
        RETURN @remaining_tab;
END;


--Views
--1. Event Details Overview
CREATE VIEW vw_EventDetails AS
SELECT
    E.event_id,
    E.event_start,
    E.event_end,
    C.contact_name,
    C.phone_number,
    V.venue_name, 
    V.street_address + ' ' + V.city + ', ' + V.state + ' ' + V.zip AS venue_address,
    E.attendee_count
FROM Event E
JOIN Contact C ON contact_id = contact_id
JOIN Venue V ON venue_id = venue_id;

--2. Venue Event Overview
CREATE VIEW vw_VenueEventOverview AS
SELECT 
V.venue_id,
V.venue_name,
V.street_address + ' ' + V.city + ', ' + V.state + ' ' + V.zip AS venue_address,
E.event_id,
E.event_start,
E.event_end

FROM Venue V
JOIN [Event] E ON venue_id = venue_id;
GO

--3. Manager Shift Summary 
CREATE VIEW vw_EmployeeShiftSummary AS
SELECT 
E.employee_id,
E.employee_name, 
E.is_manager,
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



-- Triggers
-- 1. Trigger if an event Contact is not also registered as an event Attendee
-- Every contact should be going to the event. This is to make sure if something goes wrong or if a guest is unruly, there is someone there to handle the situation
CREATE TRIGGER trg_ContactInAttendee
ON Attendee
AFTER INSERT
AS
BEGIN
    DECLARE @contact_id INT;
    DECLARE @event_id INT;

    -- Get the contact_id and event_id from the inserted record
    SELECT @contact_id = contact_id, @event_id = event_id
    FROM Event_Contact;

    -- Check if the contact_id exists for the specific event_id in the Attendee table
    IF NOT EXISTS (
        SELECT 1 
        FROM Event_Attendence
        WHERE attendee_id = @contact_id
        AND event_id = @event_id
    )
    BEGIN
        -- Raise an error if contact_id is not found for the event_id
        RAISERROR ('Error: The contact_id does not exist for the given event_id in the Attendee table.', 16, 1);
    END
END;


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

