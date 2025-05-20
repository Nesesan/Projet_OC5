
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

CREATE TABLE TEACHERS (
                          id BIGINT PRIMARY KEY,
                          last_name VARCHAR(20) NOT NULL,
                          first_name VARCHAR(20) NOT NULL,
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP
);


CREATE TABLE SESSIONS (
                          id BIGINT PRIMARY KEY,
                          name VARCHAR(50) NOT NULL,
                          date DATE NOT NULL,
                          description VARCHAR(2500) NOT NULL,
                          teacher_id BIGINT,
                          created_at TIMESTAMP,
                          updated_at TIMESTAMP,
                          CONSTRAINT fk_teacher FOREIGN KEY (teacher_id) REFERENCES TEACHERS(id)
);

CREATE TABLE PARTICIPATE (
                             session_id BIGINT NOT NULL,
                             user_id BIGINT NOT NULL,
                             PRIMARY KEY (session_id, user_id),
                             CONSTRAINT fk_participate_session FOREIGN KEY (session_id) REFERENCES SESSIONS(id),
                             CONSTRAINT fk_participate_user FOREIGN KEY (user_id) REFERENCES USERS(id)
);
