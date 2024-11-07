-- P4 

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

--Indexes
-- 1. Event_Attendence with index on attendee_id 
CREATE NONCLUSTERED INDEX idx_attendee_id ON Event_Attendence(attendee_id);

--2.  Shift based on employee_id
CREATE NONCLUSTERED INDEX idx_employee_id ON Shift(employee_id);

 --3. Venue based on capacity
CREATE NONCLUSTERED INDEX idx_total_capacity ON Venue(total_capacity);

