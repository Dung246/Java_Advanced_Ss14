CREATE DATABASE tranfer_db;
use tranfer_db;
CREATE TABLE Accounts(
AccountId VARCHAR(10) PRIMARY KEY,
FullName VARCHAR(50),
Balance DECIMAL(18, 2)
);

INSERT INTO Accounts VALUES('ACC01', 'NguyenVanA', 5000), ('ACC02', 'Tran Thi B',2000)
DELIMITER //
CREATE PROCEDURE sp_UpdateBalance(
@Id VARCHAR(10),
@Amount DECIMAL(18,2)
)
AS
BEGIN
UPDATE Accounts SET Balance=Balance+@Amount WHERE A	ccountId=@Id;
END//