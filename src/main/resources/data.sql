INSERT INTO ROLE (ROLE_NAME, ROLE_DESC, is_expression)
VALUES ('ROLE_ADMIN', '관리자', 'N'),
       ('ROLE_MANAGER', '매니저', 'N'),
       ('ROLE_USER', '사용자권한', 'N'),
       ('ROLE_DBA', '디비 관리자', 'N'),
       ('hasRole(''DBA'') or hasRole(''ADMIN'')', '총괄 관리자 및 디비 관리자', 'Y')
;

INSERT INTO Account(username, password, age)
VALUES ('admin', '{bcrypt}$2a$10$ubsYj8kR1q60cFoP/JhGeuvz2q5E3uG4Ig7SMismfySiyVPm4Y7wC', '42'),
       ('manager', '{bcrypt}$2a$10$ubsYj8kR1q60cFoP/JhGeuvz2q5E3uG4Ig7SMismfySiyVPm4Y7wC', '32'),
       ('user', '{bcrypt}$2a$10$ubsYj8kR1q60cFoP/JhGeuvz2q5E3uG4Ig7SMismfySiyVPm4Y7wC', '22'),
       ('dba', '{bcrypt}$2a$10$ubsYj8kR1q60cFoP/JhGeuvz2q5E3uG4Ig7SMismfySiyVPm4Y7wC', '12')
;

INSERT INTO account_roles(account_id, role_id)
VALUES (1, 1),
--        (1, 2),
--        (1, 3),
--        (1, 4),
       (2, 2),
--        (2, 3),
       (3, 3),
       (4, 3),
       (4, 4)
       ;

INSERT INTO resources(http_method, order_num, resource_name, resource_type)
VALUES ('', 0, '/admin/**', 'url'),
       ('', 1, '/manager', 'url'),
       ('', 2, '/user', 'url'),
       ('', 3, '/db', 'url')
;

INSERT INTO role_resources(resource_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 5)
;
