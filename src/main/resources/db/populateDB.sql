DELETE FROM user_role;
DELETE FROM meal;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meal(user_id, date_time, description, calories)
VALUES (100000, '2020-01-30 10:00', 'Завтрак', 500),
       (100000, '2020-01-30 13:00', 'Обед', 1000),
       (100000, '2020-01-30 20:00', 'Ужин', 500),
       (100000, '2020-01-31 00:00', 'Еда на граничное значение', 100),
       (100000, '2020-01-31 10:00', 'Завтрак', 1000),
       (100000, '2020-01-31 13:00', 'Обед', 500),
       (100000, '2020-01-31 20:00', 'Ужин', 410),
       (100001, '2020-01-31 14:00', 'Обед админа', 800),
       (100001, '2020-01-31 20:00', 'Ужин админа', 910);
