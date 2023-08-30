CREATE PROCEDURE remove_person_from_book(IN book_id INT) AS
$$
BEGIN
UPDATE Book
SET personId = NULL,
    issued   = NULL
WHERE id = book_id;
END;
$$
LANGUAGE plpgsql;

CREATE PROCEDURE update_book_person_relationship(IN book_id INT, IN person_id INT) AS
$$
BEGIN
UPDATE Book
SET personId = person_id
WHERE id = book_id;
END;
$$
LANGUAGE plpgsql;

CREATE PROCEDURE update_person_status(IN person_id INTEGER,IN person_status VARCHAR(6))
AS
$$
BEGIN
    UPDATE person
    SET status = person_status
    WHERE id = person_id;
END;
$$
LANGUAGE plpgsql;

CREATE PROCEDURE get_person_by_fullName (IN person_fullname CHARACTER(200), OUT person_id INT)
AS
$$
BEGIN
    SELECT id INTO person_id
    FROM person
    WHERE fullname LIKE person_fullname;

    person_id := COALESCE(person_id, 0);
END;
$$
LANGUAGE plpgsql;



-- encrypted_user_password = $2a$10$OQzIha/VdxJjwQ3LwnSCj./7b5kWnHEtzqvJ4KEtOAysQTJBUPKq6
-- origin_user_password = one
-- all users has one common password

INSERT INTO person (fullName, password, date_of_birth, role, status, created_at, updated_at, created_who)
VALUES
    ('John Smith', '$2a$10$OQzIha/VdxJjwQ3LwnSCj./7b5kWnHEtzqvJ4KEtOAysQTJBUPKq6', '1990-03-15', 'ROLE_USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'REGISTERED'),
    ('Alice Johnson', '$2a$10$OQzIha/VdxJjwQ3LwnSCj./7b5kWnHEtzqvJ4KEtOAysQTJBUPKq6', '1985-07-10', 'ROLE_USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'REGISTERED'),
    ('Michael Brown', '$2a$10$OQzIha/VdxJjwQ3LwnSCj./7b5kWnHEtzqvJ4KEtOAysQTJBUPKq6', '1992-11-25', 'ROLE_USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN'),
    ('Emily Davis', '$2a$10$OQzIha/VdxJjwQ3LwnSCj./7b5kWnHEtzqvJ4KEtOAysQTJBUPKq6', '1988-05-01', 'ROLE_USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN'),
    ('Daniel Wilson', '$2a$10$OQzIha/VdxJjwQ3LwnSCj./7b5kWnHEtzqvJ4KEtOAysQTJBUPKq6', '1976-09-20', 'ROLE_USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'REGISTERED'),
    ('Sophia Lee', '$2a$10$OQzIha/VdxJjwQ3LwnSCj./7b5kWnHEtzqvJ4KEtOAysQTJBUPKq6', '1993-04-08', 'ROLE_USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN'),
    ('James Martinez', '$2a$10$OQzIha/VdxJjwQ3LwnSCj./7b5kWnHEtzqvJ4KEtOAysQTJBUPKq6', '1998-12-30', 'ROLE_USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN'),
    ('Olivia Anderson', '$2a$10$OQzIha/VdxJjwQ3LwnSCj./7b5kWnHEtzqvJ4KEtOAysQTJBUPKq6', '1987-02-05', 'ROLE_USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN'),
    ('Alexander Taylor', '$2a$10$OQzIha/VdxJjwQ3LwnSCj./7b5kWnHEtzqvJ4KEtOAysQTJBUPKq6', '1991-06-12', 'ROLE_USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'REGISTERED'),
    ('Emma Hernandez', '$2a$10$OQzIha/VdxJjwQ3LwnSCj./7b5kWnHEtzqvJ4KEtOAysQTJBUPKq6', '1979-08-18', 'ROLE_USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN');

INSERT INTO book (name, author, year, personId, issued, created_at, updated_at, created_who)
VALUES
        -- When user has expired a book
       ('The Great Gatsby', 'F. Scott Fitzgerald', 1925, 1, '2023-08-01 00:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('The Great Gatsby', 'F. Scott Fitzgerald', 1925, 1, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('To Kill a Mockingbird', 'Harper Lee', 1960, 3, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('1984', 'George Orwell', 1949, 5, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('Pride and Prejudice', 'Jane Austen', 1813, 7, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('The Catcher in the Rye', 'J.D. Salinger', 1951, 9, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('The Hobbit', 'J.R.R. Tolkien', 1937, 4, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('Brave New World', 'Aldous Huxley', 1932, 6, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('The Lord of the Rings', 'J.R.R. Tolkien', 1954, 8, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('To Kill a Mockingbird', 'Harper Lee', 1960, 10, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('Crime and Punishment', 'Fyodor Dostoevsky', 1866, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('The Picture of Dorian Gray', 'Oscar Wilde', 1890, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('The Great Expectations', 'Charles Dickens', 1861, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 1967, NULL, NULL, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP, 'Admin'),
       ('The Brothers Karamazov', 'Fyodor Dostoevsky', 1880, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('The Old Man and the Sea', 'Ernest Hemingway', 1952, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('Moby-Dick', 'Herman Melville', 1851, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('War and Peace', 'Leo Tolstoy', 1869, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin'),
       ('The Divine Comedy', 'Dante Alighieri', 1320, NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Admin');
