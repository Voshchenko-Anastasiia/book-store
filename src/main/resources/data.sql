-- 1. SYSTEM ADMINISTRATOR & EMPLOYEES (5 Accounts) (All passwords are: password123)

-- admin@bookstore.com
INSERT INTO users (DTYPE, email, name, password, role)
VALUES ('Employee', 'admin@bookstore.com', 'System Administrator', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'ADMIN');

-- john.manager@bookstore.com
INSERT INTO users (DTYPE, email, name, password, role)
VALUES ('Employee', 'john.manager@bookstore.com', 'John Manager', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'EMPLOYEE');

-- alice.clerk@bookstore.com
INSERT INTO users (DTYPE, email, name, password, role)
VALUES ('Employee', 'alice.clerk@bookstore.com', 'Alice Smith', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'EMPLOYEE');

-- bob.support@bookstore.com
INSERT INTO users (DTYPE, email, name, password, role)
VALUES ('Employee', 'bob.support@bookstore.com', 'Bob Jones', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'EMPLOYEE');

-- carol.inventory@bookstore.com
INSERT INTO users (DTYPE, email, name, password, role)
VALUES ('Employee', 'carol.inventory@bookstore.com', 'Carol White', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'EMPLOYEE');


-- 2. CLIENTS / CUSTOMERS (All passwords are: password123)

INSERT INTO users (DTYPE, email, name, password, role) VALUES
('Client', 'client1@gmail.com', 'Alexander Wright', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'CLIENT'),
('Client', 'client2@gmail.com', 'Emma Watson', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'CLIENT'),
('Client', 'client3@gmail.com', 'Liam Neeson', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'CLIENT'),
('Client', 'client4@gmail.com', 'Olivia Wilde', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'CLIENT'),
('Client', 'client5@gmail.com', 'Noah Centineo', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'CLIENT');

-- 3. BOOKS

INSERT INTO books (name, author, genre, age_group, price, publication_year, number_of_pages, language, description, characteristics) VALUES
('The Selfish Gene', 'Richard Dawkins', 'Science', 'ADULT', 19.99, '1976-01-01', 360, 'ENGLISH', 'A foundational work on evolutionary biology.', 'Hardcover'),
('The God Delusion', 'Richard Dawkins', 'Philosophy', 'ADULT', 18.50, '2006-01-01', 463, 'ENGLISH', 'A philosophical critique of religion.', 'Paperback'),
('The Blind Watchmaker', 'Richard Dawkins', 'Science', 'ADULT', 16.00, '1986-01-01', 311, 'ENGLISH', 'An explanation of natural selection.', 'Paperback'),
('A Brief History of Time', 'Stephen Hawking', 'Science', 'ADULT', 14.99, '1988-01-01', 256, 'ENGLISH', 'Cosmology for general readers.', 'Hardcover'),
('The Universe in a Nutshell', 'Stephen Hawking', 'Science', 'ADULT', 22.00, '2001-01-01', 216, 'ENGLISH', 'Theoretical physics developments illustrated.', 'Hardcover'),
('Brief Answers to the Big Questions', 'Stephen Hawking', 'Science', 'ADULT', 17.95, '2018-01-01', 256, 'ENGLISH', 'Final thoughts on universes biggest challenges.', 'Paperback');

INSERT INTO books (name, author, genre, age_group, price, publication_year, number_of_pages, language, description, characteristics) VALUES
('Slash: The Autobiography', 'Slash', 'Biography', 'ADULT', 21.00, '2007-01-01', 480, 'ENGLISH', 'Life of the iconic Guns N Roses guitarist.', 'Paperback'),
('Lives of Brian', 'Brian Johnson', 'Biography', 'ADULT', 24.99, '2022-01-01', 384, 'ENGLISH', 'Memoir of AC/DC front man.', 'Hardcover'),
('Rainbow in the Dark', 'Ronnie James Dio', 'Biography', 'ADULT', 20.00, '2021-01-01', 320, 'ENGLISH', 'Heavy metal icons life story.', 'Hardcover'),
('I Am Ozzy', 'Ozzy Osbourne', 'Biography', 'ADULT', 18.99, '2009-01-01', 416, 'ENGLISH', 'Unfiltered memoir of the Prince of Darkness.', 'Paperback'),
('Brian May: The Definitive Biography', 'Laura Jackson', 'Biography', 'ADULT', 35.00, '2017-01-01', 256, 'ENGLISH', 'History of Queen captured in stereoscopic photos.', 'Hardcover with stereoscope included'),
('Let Love Rule', 'Lenny Kravitz', 'Biography', 'ADULT', 22.50, '2020-01-01', 272, 'ENGLISH', 'Reflections on the first 25 years of life.', 'Hardcover'),
('Me', 'Elton John', 'Biography', 'ADULT', 19.99, '2019-01-01', 384, 'ENGLISH', 'The official, dramatic autobiography.', 'Paperback'),
('Blues All Around Me', 'B.B. King', 'Biography', 'ADULT', 16.95, '1996-01-01', 368, 'ENGLISH', 'The life and legacy of a blues icon.', 'Paperback');

INSERT INTO books (name, author, genre, age_group, price, publication_year, number_of_pages, language, description, characteristics) VALUES
('Kobzar', 'Taras Shevchenko', 'Poetry', 'ADULT', 15.00, '1840-01-01', 400, 'UKRAINIAN', 'The core literary masterpiece of Ukrainian verse.', 'Premium Gift Edition'),
('Anthology of Ukrainian Poetry', 'Ivan Malkovych', 'Poetry', 'ADULT', 25.00, '2016-01-01', 600, 'UKRAINIAN', 'Massive collection of classical Ukrainian verse.', 'Fabric hardcover'),
('Selected Poems', 'Lina Kostenko', 'Poetry', 'ADULT', 12.50, '2012-01-01', 320, 'UKRAINIAN', 'Deep and resonant modern poetry.', 'Hardcover'),
('Green Gospel', 'Bohdan-Ihor Antonych', 'Poetry', 'ADULT', 11.00, '2009-01-01', 192, 'UKRAINIAN', 'Lyrical pagan and nature themes from Western Ukraine.', 'Pocket format'),
('Letters from Ukraine', 'Vasyl Stus', 'Poetry', 'ADULT', 13.40, '2015-01-01', 240, 'UKRAINIAN', 'Dissident prison poetry and personal notes.', 'Hardcover'),
('Selected Verse', 'Vasyl Symonenko', 'Poetry', 'TEEN', 10.50, '2010-01-01', 180, 'UKRAINIAN', 'Emotional, sincere patriotic and romantic text.', 'Paperback'),
('The Book of Grace', 'Attila Mohylny', 'Poetry', 'ADULT', 14.00, '1993-01-01', 150, 'UKRAINIAN', 'Rare contemporary Kyiv underground poetry.', 'Paperback'),
('Poems of the Sun', 'Ivan Drach', 'Poetry', 'ADULT', 12.00, '1962-01-01', 210, 'UKRAINIAN', 'Avant-garde Soviet-era Ukrainian poetry.', 'Hardcover'),
('Selected Lyrics', 'Borys Oliynyk', 'Poetry', 'ADULT', 11.50, '1975-01-01', 230, 'UKRAINIAN', 'Traditional values and classical style stanzas.', 'Hardcover'),
('A-BA-BA-HA-LA-MA-HA: Best Verse', 'Various Authors', 'Poetry', 'CHILD', 20.00, '2020-01-01', 120, 'UKRAINIAN', 'Highly illustrated children anthology series.', 'Large format color plates');

INSERT INTO books (name, author, genre, age_group, price, publication_year, number_of_pages, language, description, characteristics) VALUES
('Effective Java (3rd Edition)', 'Joshua Bloch', 'Java', 'ADULT', 45.00, '2017-12-01', 412, 'ENGLISH', 'Best practices guide for explicit language features.', 'Technical Guide'),
('Java: The Complete Reference', 'Herbert Schildt', 'Java', 'ADULT', 55.00, '2021-11-01', 1248, 'ENGLISH', 'Monolithic comprehensive map of the full runtime language.', 'Reference Manual'),
('Head First Java', 'Kathy Sierra', 'Java', 'TEEN', 42.00, '2022-05-01', 720, 'ENGLISH', 'Highly interactive brain-friendly visual coding map.', 'Softcover Workbook'),
('Java Concurrency in Practice', 'Brian Goetz', 'Java', 'ADULT', 48.50, '2006-05-01', 384, 'ENGLISH', 'The holy grail reference for multi-threaded systems architecture.', 'Paperback Core Tech'),
('Core Java Volume I', 'Cay S. Horstmann', 'Java', 'ADULT', 50.00, '2022-03-01', 840, 'ENGLISH', 'Deep fundamental guide on libraries and internal logic.', 'Hardcover Reference'),
('Thinking in Java', 'Bruce Eckel', 'Java', 'ADULT', 38.00, '2006-02-01', 1480, 'ENGLISH', 'Object oriented principles unpacked directly.', 'Paperback');

INSERT INTO books (name, author, genre, age_group, price, publication_year, number_of_pages, language, description, characteristics) VALUES
('Clean Code', 'Robert C. Martin', 'Development', 'ADULT', 39.99, '2008-08-01', 464, 'ENGLISH', 'Craftsmanship manual for clear, maintainable architecture parsing.', 'Classic Paperback'),
('The Clean Coder', 'Robert C. Martin', 'Development', 'ADULT', 34.99, '2011-05-01', 242, 'ENGLISH', 'A code of conduct and professional guide for software engineers.', 'Softcover'),
('Clean Architecture', 'Robert C. Martin', 'Development', 'ADULT', 42.00, '2017-09-01', 432, 'ENGLISH', 'Universal design principles for system abstraction layers.', 'Hardcover Manual'),
('Clean Agile', 'Robert C. Martin', 'Development', 'ADULT', 29.95, '2019-10-01', 240, 'ENGLISH', 'Back to principles explanation of genuine agile practices.', 'Paperback'),
('Clean Craftsmanship', 'Robert C. Martin', 'Development', 'ADULT', 38.00, '2021-09-01', 352, 'ENGLISH', 'The core disciplines and practices of software developers.', 'Hardcover'),
('Agile Software Development', 'Robert C. Martin', 'Development', 'ADULT', 65.00, '2002-10-01', 529, 'ENGLISH', 'Comprehensive patterns and case studies walkthrough.', 'Hardcover Reference');

INSERT INTO books (name, author, genre, age_group, price, publication_year, number_of_pages, language, description, characteristics) VALUES
('The Pragmatic Programmer', 'Andrew Hunt', 'Development', 'ADULT', 44.95, '2019-09-01', 352, 'ENGLISH', 'Your journey to mastery guidelines.', 'Hardcover'),
('Design Patterns', 'Erich Gamma', 'Development', 'ADULT', 52.00, '1994-10-01', 395, 'ENGLISH', 'Elements of reusable object-oriented software patterns.', 'Hardcover Classic'),
('Introduction to Algorithms', 'Thomas H. Cormen', 'Science', 'ADULT', 85.00, '2022-04-01', 1312, 'ENGLISH', 'Detailed analytical breakdown of complex data workflows.', 'Heavy Text Book'),
('Refactoring', 'Martin Fowler', 'Development', 'ADULT', 47.99, '2018-11-01', 448, 'ENGLISH', 'Improving design of existing source logic structures cleanly.', 'Hardcover'),
('The Lean Startup', 'Eric Ries', 'Business', 'ADULT', 24.99, '2011-09-01', 336, 'ENGLISH', 'Constant innovation for business structures.', 'Paperback'),
('Zero to One', 'Peter Thiel', 'Business', 'ADULT', 21.00, '2014-09-01', 224, 'ENGLISH', 'Notes on startups, or how to build the future.', 'Hardcover'),
('Good to Great', 'Jim Collins', 'Business', 'ADULT', 26.50, '2001-10-01', 400, 'ENGLISH', 'Why some companies make the leap and others do not.', 'Hardcover'),
('Steve Jobs', 'Walter Isaacson', 'Biography', 'ADULT', 20.00, '2011-10-01', 656, 'ENGLISH', 'Exclusive biography of the Apple co-founder.', 'Paperback'),
('1984', 'George Orwell', 'Fiction', 'TEEN', 10.99, '1949-06-01', 328, 'ENGLISH', 'Dystopian nightmare tracking total societal monitoring.', 'Paperback'),
('Animal Farm', 'George Orwell', 'Fiction', 'TEEN', 8.99, '1945-08-01', 112, 'ENGLISH', 'Satirical allegorical fable on authority manipulation.', 'Pocket edition'),
('Brave New World', 'Aldous Huxley', 'Fiction', 'ADULT', 12.50, '1932-01-01', 288, 'ENGLISH', 'Utopian bio-engineered tracking of human class creation.', 'Paperback'),
('Fahrenheit 451', 'Ray Bradbury', 'Fiction', 'TEEN', 11.99, '1953-10-01', 256, 'ENGLISH', 'A society where books are outlawed and burned.', 'Paperback'),
('The Hobbit', 'J.R.R. Tolkien', 'Fiction', 'CHILD', 14.95, '1937-09-01', 310, 'ENGLISH', 'There and back again epic fantasy precursor.', 'Illustrated Hardcover'),
('The Lord of the Rings', 'J.R.R. Tolkien', 'Fiction', 'TEEN', 30.00, '1954-07-01', 1216, 'ENGLISH', 'The single unified master edition trilogy text.', 'Paperback Compilation'),
('Dune', 'Frank Herbert', 'Fiction', 'TEEN', 15.99, '1965-06-01', 604, 'ENGLISH', 'Interstellar planetary desert political mastery saga.', 'Paperback'),
('Neuromancer', 'William Gibson', 'Fiction', 'ADULT', 9.99, '1984-07-01', 271, 'ENGLISH', 'The foundational masterpiece archetype of cyberpunk.', 'Mass Market Edition'),
('Snow Crash', 'Neal Stephenson', 'Fiction', 'ADULT', 13.50, '1992-06-01', 470, 'ENGLISH', 'Virtual metaverse and linguistic virus tracing elements.', 'Paperback'),
('Sapiens', 'Yuval Noah Harari', 'History', 'ADULT', 22.00, '2011-01-01', 443, 'ENGLISH', 'A brief history of humankind.', 'Paperback'),
('Homo Deus', 'Yuval Noah Harari', 'History', 'ADULT', 23.50, '2015-01-01', 450, 'ENGLISH', 'A brief history of tomorrow.', 'Hardcover'),
('21 Lessons for the 21st Century', 'Yuval Noah Harari', 'History', 'ADULT', 21.00, '2018-08-01', 368, 'ENGLISH', 'Navigating modern immediate existential questions.', 'Paperback'),
('The Silk Roads', 'Peter Frankopan', 'History', 'ADULT', 16.99, '2015-08-01', 656, 'ENGLISH', 'A major new history of the world center axes.', 'Paperback');