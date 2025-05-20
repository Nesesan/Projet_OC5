DELETE FROM PARTICIPATE;
DELETE FROM SESSIONS;
DELETE FROM USERS;
DELETE FROM TEACHERS;

INSERT INTO TEACHERS (id, first_name, last_name, created_at, updated_at)
VALUES
    (1, 'Margot', 'DELAHAYE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Hélène', 'THIERCELIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'Jean', 'Dupont', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'Sophie', 'Martin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO USERS (id, email, last_name, first_name, password, admin, created_at, updated_at)
VALUES
    (1, 'yoga@studio.com', 'Admin', 'Admin', 'test!1234', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'lounss@mail.com', 'Lounss', 'Benz', 'password', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'user1@example.com', 'Dupont', 'Claire', 'password123', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'admin@example.com', 'Bernard', 'Luc', 'adminpass456', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO SESSIONS (id, name, date, description, teacher_id, created_at, updated_at)
VALUES
    (1, 'Intro to Spring', CURRENT_DATE, 'Découverte du framework Spring pour les débutants.', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Java Avancé', CURRENT_DATE, 'Approfondissement des concepts Java pour développeurs confirmés.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
