
DROP TABLE IF EXISTS PARTICIPATE;
DROP TABLE IF EXISTS SESSIONS;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS TEACHERS;


CREATE TABLE TEACHERS (
                          id BIGINT PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP
);

CREATE TABLE USERS (
                       id BIGINT PRIMARY KEY,
                       email VARCHAR(50) NOT NULL UNIQUE,
                       last_name VARCHAR(20) NOT NULL,
                       first_name VARCHAR(20) NOT NULL,
                       password VARCHAR(120) NOT NULL,
                       admin BOOLEAN NOT NULL,
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP
);

CREATE TABLE SESSIONS (
                          id BIGINT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          date DATE NOT NULL,
                          description TEXT,
                          teacher_id BIGINT,
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP,
                          CONSTRAINT FK_TEACHER FOREIGN KEY (teacher_id) REFERENCES TEACHERS(id)
);

CREATE TABLE PARTICIPATE (
                             session_id BIGINT NOT NULL,
                             user_id BIGINT NOT NULL,
                             PRIMARY KEY (session_id, user_id),
                             CONSTRAINT FK_PARTICIPATE_SESSION FOREIGN KEY (session_id) REFERENCES SESSIONS(id),
                             CONSTRAINT FK_PARTICIPATE_USER FOREIGN KEY (user_id) REFERENCES USERS(id)
);
