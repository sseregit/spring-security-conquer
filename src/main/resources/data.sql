INSERT INTO ROLE (ROLE_NAME, ROLE_DESC, is_expression)
VALUES ('ROLE_ADMIN', '관리자', 'N'),
       ('ROLE_MANAGER', '매니저', 'N'),
       ('ROLE_USER', '사용자권한', 'N');

INSERT INTO Account(username, password, age)
VALUES ('admin', '{bcrypt}$2a$10$ubsYj8kR1q60cFoP/JhGeuvz2q5E3uG4Ig7SMismfySiyVPm4Y7wC', '42'),
       ('manager', '{bcrypt}$2a$10$ubsYj8kR1q60cFoP/JhGeuvz2q5E3uG4Ig7SMismfySiyVPm4Y7wC', '32'),
       ('user', '{bcrypt}$2a$10$ubsYj8kR1q60cFoP/JhGeuvz2q5E3uG4Ig7SMismfySiyVPm4Y7wC', '22');

INSERT INTO account_roles(account_id, role_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (3, 3);
