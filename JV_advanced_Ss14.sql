CREATE DATABASE tranfer_db;
USE tranfer_db;

CREATE TABLE Accounts(
    AccountId VARCHAR(10) PRIMARY KEY,
    FullName VARCHAR(50),
    Balance DECIMAL(18, 2)
);

INSERT INTO Accounts VALUES
('ACC01', 'Đỗ Thị Thuỳ Trang', 5000),
('ACC02', 'Trần Mạnh Hồng', 2000);

DELIMITER //

CREATE PROCEDURE sp_UpdateBalance(
    IN Id VARCHAR(10),
    IN Amount DECIMAL(18,2)
)
BEGIN
    UPDATE Accounts
    SET Balance = Balance + Amount
    WHERE AccountId = Id;
END //

DELIMITER ;
