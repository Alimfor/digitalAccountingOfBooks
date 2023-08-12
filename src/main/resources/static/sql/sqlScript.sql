CREATE TABLE person (
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    fullName VARCHAR(200) NOT NULL UNIQUE ,
    date_of_birth DATE NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE book(
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    name VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    personId INT REFERENCES person(id) ON DELETE SET NULL,
    issued TIMESTAMP
);

CREATE PROCEDURE remove_person_frombook(IN book_id INT)
AS
$$
BEGIN
UPDATE Book
SET personId = NULL,
    issued = NULL
WHERE id = book_id;
END;
$$ LANGUAGE plpgsql;

CREATE PROCEDURE update_book_person_relationship(IN book_id INT, IN person_id INT)
AS
$$
BEGIN
UPDATE Book
SET personId = person_id
WHERE id = book_id;
END;
$$ LANGUAGE plpgsql;

INSERT INTO person (fullName, date_of_birth, created_at) VALUES
('John Smith', '1990-03-15', CURRENT_TIMESTAMP),
('Alice Johnson', '1985-07-10', CURRENT_TIMESTAMP),
('Michael Brown', '1992-11-25', CURRENT_TIMESTAMP),
('Emily Davis', '1988-05-01', CURRENT_TIMESTAMP),
('Daniel Wilson', '1976-09-20', CURRENT_TIMESTAMP),
('Sophia Lee', '1993-04-08', CURRENT_TIMESTAMP),
('James Martinez', '1998-12-30', CURRENT_TIMESTAMP),
('Olivia Anderson', '1987-02-05', CURRENT_TIMESTAMP),
('Alexander Taylor', '1991-06-12', CURRENT_TIMESTAMP),
('Emma Hernandez', '1979-08-18', CURRENT_TIMESTAMP);

INSERT INTO book (name, author, year, personId) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 1925, 1),
('To Kill a Mockingbird', 'Harper Lee', 1960, 3),
('1984', 'George Orwell', 1949, 5),
('Pride and Prejudice', 'Jane Austen', 1813, 7),
('The Catcher in the Rye', 'J.D. Salinger', 1951, 9,
('The Hobbit', 'J.R.R. Tolkien', 1937, 4),
('Brave New World', 'Aldous Huxley', 1932, 6),
('The Lord of the Rings', 'J.R.R. Tolkien', 1954, 8),
('To Kill a Mockingbird', 'Harper Lee', 1960, 10),
('Crime and Punishment', 'Fyodor Dostoevsky', 1866, NULL),
('The Picture of Dorian Gray', 'Oscar Wilde', 1890, NULL),
('The Great Expectations', 'Charles Dickens', 1861, NULL),
('One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 1967, NULL),
('The Brothers Karamazov', 'Fyodor Dostoevsky', 1880, NULL),
('The Old Man and the Sea', 'Ernest Hemingway', 1952, NULL),
('Moby-Dick', 'Herman Melville', 1851, NULL),
('War and Peace', 'Leo Tolstoy', 1869, NULL),
('The Divine Comedy', 'Dante Alighieri', 1320, NULL);

