-- Teachers data (avec IDs fixes)
INSERT INTO TEACHERS (id, first_name, last_name, created_at, updated_at)
VALUES
    (1, 'Margot', 'DELAHAYE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Hélène', 'THIERCELIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'Jean', 'Dupont', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 'Sophie', 'Martin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Users data (avec IDs fixes et mots de passe encodés avec BCrypt)
INSERT INTO USERS (id, email, last_name, first_name, password, admin, created_at, updated_at)
VALUES
    (1, 'yoga@studio.com', 'Admin', 'Admin', '$2a$10$fVYAbt.j6x6kDpW7oXM4b.T6zzTKJHVeUPs3hekDUkSq0SOQKF6cG', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- test!1234
    (2, 'lounss@mail.com', 'Lounss', 'Benz', '$2a$10$Rlm6o2kQzHRNYYqfUCPAMuEQUgC4kl31rsr.FyyIHpqaKebGbiDfq', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- password
    (3, 'user1@example.com', 'Dupont', 'Claire', '$2a$10$W9hfFkRfq3xIvT2V1hM1lOmrUeXY3DxL7jQ62bRT.CepcSY9zDqeC', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- password123
    (4, 'admin@example.com', 'Bernard', 'Luc', '$2a$10$Z3eZrGuWmjSV8xFC4MYDyuk5cybEZlhzLSHyBP78cNID9v8mVY8Mi', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); -- adminpass456

-- Sessions data (avec IDs fixes et relations correctes avec les enseignants)
INSERT INTO SESSIONS (id, name, date, description, teacher_id, created_at, updated_at)
VALUES
    (1, 'Intro to Spring', CURRENT_DATE, 'Découverte du framework Spring pour les débutants.', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Java Avancé', CURRENT_DATE, 'Approfondissement des concepts Java pour développeurs confirmés.', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
