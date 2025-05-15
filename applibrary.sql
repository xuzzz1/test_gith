CREATE DATABASE appLibrary;
go
USE appLibrary;
go
-- Bảng Users

CREATE TABLE Users (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    Username VARCHAR(50) UNIQUE NOT NULL, -- Dùng làm thông tin đăng nhập
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Email VARCHAR(100) , -- Thêm email, không để trống, đảm bảo duy nhất
    Gender VARCHAR(10) CHECK (Gender IN ('Male', 'Female')), -- Giới tính chỉ nhận các giá trị hợp lệ
    StudentID VARCHAR(20) , -- Mã số sinh viên, có thể là NULL nếu không phải sinh viên
    PhoneNumber VARCHAR(15),
    Password VARCHAR(255), -- Lưu mật khẩu đã mã hóa
    Role VARCHAR(20) NOT NULL DEFAULT 'Users' -- Vai trò mặc định là 'Users'
);



CREATE TABLE Book (
    BookID INT IDENTITY(1,1) PRIMARY KEY,
    Title VARCHAR(255),
    AuthorName VARCHAR(100), -- Lưu tên tác giả trực tiếp
    CategoryName VARCHAR(100), -- Lưu tên danh mục trực tiếp
    Quantity INT CHECK (Quantity >= 0),
    Status VARCHAR(50) CHECK (Status IN ('Available', 'Borrowed')),
    ImageUrl VARCHAR(255), -- Lưu đường dẫn ảnh
    Description TEXT -- Mô tả sách (Bỏ dấu chấm phẩy)
);


CREATE TABLE Reserve_Return (
    ReserveID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    BookID INT NOT NULL,
    ReserveDate DATE NOT NULL DEFAULT GETDATE(),
    DueDate DATE NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE,
    FOREIGN KEY (BookID) REFERENCES Book(BookID) ON DELETE CASCADE
);

CREATE TABLE Rate (
    RateID INT PRIMARY KEY IDENTITY(1,1),
    UserID INT NOT NULL,
    BookID INT NOT NULL,
    RateStar INT CHECK (RateStar BETWEEN 1 AND 5),
    Comment TEXT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE,
    FOREIGN KEY (BookID) REFERENCES Book(BookID) ON DELETE CASCADE
);
-- Thêm dữ liệu vào bảng Users với Role

INSERT INTO Users (Username, FirstName, LastName, Email, Gender, StudentID, PhoneNumber, Password, Role) VALUES
('an', 'Nguyen', 'An', 'an@example.com', 'Male', 'SV001', '0987654321', '1', 'AD'),   -- Admin
('binh', 'Tran', 'Binh', 'binh@example.com', 'Male', 'SV002', '0977123456', '2', 'Users'), -- Người dùng thường
('chau', 'Le', 'Chau', 'chau@example.com', 'Female', 'SV003', '0967234567', '3', 'Users');   -- Người dùng thường


-- Thêm dữ liệu vào bảng Category

-- Thêm dữ liệu vào bảng Author

DELETE FROM Book;
SELECT * FROM Book;
-- Thêm dữ liệu vào bảng Book
-- Thêm dữ liệu vào bảng Book
INSERT INTO Book (Title, AuthorName, CategoryName, Quantity, Status, ImageUrl, Description) VALUES
( 'Foundation', 'Isaac Asimov', 'Science Fiction', 5, 'Available', '1.jpg', 'A classic sci-fi novel about the fall and rise of civilizations.'),
( 'A Brief History of Time', 'Stephen Hawking', 'Science', 3, 'Available', '2.jpg', 'An exploration of the universe, black holes, and time.'),
( 'Inferno', 'Dan Brown', 'Mystery', 4, 'Borrowed', '3.jpg', 'A thrilling mystery with historical and literary elements.'),
('Sapiens', 'Yuval Noah Harari', 'History', 2, 'Borrowed', '4.jpg', 'A brief history of humankind.'),
( 'The Catcher in the Rye', 'J.D. Salinger', 'Classic', 3, 'Available', '5.jpg', 'A coming-of-age novel about teenage alienation.'),
( 'To Kill a Mockingbird', 'Harper Lee', 'Classic', 3, 'Available', '6.jpg', 'A story of racial injustice and moral growth.'),
( '1984', 'George Orwell', 'Dystopian', 2, 'Borrowed', '7.jpg', 'A novel about totalitarianism and surveillance.'),
( 'Brave New World', 'Aldous Huxley', 'Dystopian', 1, 'Available', '8.jpg', 'A futuristic society controlled by technology and conditioning.'),
( 'The Great Gatsby', 'F. Scott Fitzgerald', 'Classic', 4, 'Borrowed', '9.jpg', 'A critique of the American Dream.'),
( 'Moby-Dick', 'Herman Melville', 'Adventure', 3, 'Available', '10.jpg', 'The tale of Captain Ahab and his pursuit of the white whale.'),
( 'War and Peace', 'Leo Tolstoy', 'Historical Fiction', 1, 'Available', '11.jpg', 'A sweeping novel set during the Napoleonic Wars.'),
('Crime and Punishment', 'Fyodor Dostoevsky', 'Psychological Fiction', 2, 'Borrowed', '12.jpg', 'A psychological exploration of guilt and redemption.'),
( 'Pride and Prejudice', 'Jane Austen', 'Romance', 3, 'Available', '13.jpg', 'A classic romantic novel of manners.'),
('The Hobbit', 'J.R.R. Tolkien', 'Fantasy', 4, 'Borrowed', '14.jpg', 'The adventure of Bilbo Baggins in Middle-earth.'),
('The Lord of the Rings', 'J.R.R. Tolkien', 'Fantasy', 4, 'Borrowed', '15.jpg', 'An epic fantasy trilogy about the battle against Sauron.');


-- Thêm dữ liệu vào bảng Reserve_Return
INSERT INTO Reserve_Return ( UserID, BookID, ReserveDate, DueDate) VALUES
( 1, 3, '2024-03-01', '2024-03-15'),
( 2, 4, '2024-03-05', '2024-03-20');

-- Thêm dữ liệu vào bảng Rate
INSERT INTO Rate ( UserID, BookID, RateStar, Comment) VALUES
( 1, 1, 5, 'Amazing sci-fi novel!'),
( 2, 2, 4, 'Very informative and inspiring.'),
( 3, 3, 3, 'Good book but a bit complex.'),
( 1, 4, 5, 'One of the best history books!');



