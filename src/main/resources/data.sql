INSERT INTO Account(username, password, age, roles)
VALUES ('user', '{bcrypt}$2a$10$ubsYj8kR1q60cFoP/JhGeuvz2q5E3uG4Ig7SMismfySiyVPm4Y7wC', '12','ROLE_USER'),
('manager', '{bcrypt}$2a$10$ubsYj8kR1q60cFoP/JhGeuvz2q5E3uG4Ig7SMismfySiyVPm4Y7wC', '32', 'ROLE_MANAGER'),
('admin', '{bcrypt}$2a$10$ubsYj8kR1q60cFoP/JhGeuvz2q5E3uG4Ig7SMismfySiyVPm4Y7wC', '40', 'ROLE_ADMIN');