-- Table des utilisateurs
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       address VARCHAR(255) NOT NULL,
                       is_admin BOOLEAN NOT NULL
);

-- Table des demandes d'amis
CREATE TABLE friend_requests (
                                 id SERIAL PRIMARY KEY,
                                 sender_id INT NOT NULL,
                                 receiver_id INT NOT NULL,
                                 status VARCHAR(20) NOT NULL,
                                 CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users (id) ON DELETE CASCADE,
                                 CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Table des amitiés
CREATE TABLE friendships (
                             id SERIAL PRIMARY KEY,
                             user1_id INT NOT NULL,
                             user2_id INT NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             CONSTRAINT fk_user1 FOREIGN KEY (user1_id) REFERENCES users (id) ON DELETE CASCADE,
                             CONSTRAINT fk_user2 FOREIGN KEY (user2_id) REFERENCES users (id) ON DELETE CASCADE,
                             CONSTRAINT valid_friendship CHECK (user1_id <> user2_id)
);

-- Table des thèmes
CREATE TABLE themes (
                        theme_id SERIAL PRIMARY KEY,
                        theme_name VARCHAR(255) NOT NULL,
                        theme_description TEXT,
                        is_main BOOLEAN NOT NULL,
                        parent_theme_id INT,
                        CONSTRAINT fk_parent_theme FOREIGN KEY (parent_theme_id) REFERENCES themes (theme_id) ON DELETE SET NULL
);

-- Table des catégories
CREATE TABLE categories (
                            category_id SERIAL PRIMARY KEY,
                            category_name VARCHAR(255) NOT NULL,
                            category_description TEXT,
                            theme_id INT NOT NULL,
                            CONSTRAINT fk_theme FOREIGN KEY (theme_id) REFERENCES themes (theme_id) ON DELETE CASCADE
);

-- Table des questions
CREATE TABLE questions (
                           question_id SERIAL PRIMARY KEY,
                           question TEXT NOT NULL,
                           answer_a VARCHAR(255) NOT NULL,
                           answer_b VARCHAR(255) NOT NULL,
                           answer_c VARCHAR(255) NOT NULL,
                           answer_d VARCHAR(255) NOT NULL,
                           correct_answer CHAR(1) NOT NULL CHECK (correct_answer IN ('A', 'B', 'C', 'D')),
                           category_id INT NOT NULL,
                           main_theme_id INT NOT NULL,
                           CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories (category_id) ON DELETE CASCADE,
                           CONSTRAINT fk_main_theme FOREIGN KEY (main_theme_id) REFERENCES themes (theme_id) ON DELETE CASCADE
);

-- Table des quiz
CREATE TABLE quizzes (
                         quiz_id SERIAL PRIMARY KEY,
                         quiz_code UUID NOT NULL UNIQUE,
                         user_id INT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Table des réponses des utilisateurs
CREATE TABLE user_answers (
                              answer_id SERIAL PRIMARY KEY,
                              quiz_id INT NOT NULL,
                              question_id INT NOT NULL,
                              user_response CHAR(1) CHECK (user_response IN ('A', 'B', 'C', 'D')),
                              is_correct BOOLEAN,
                              CONSTRAINT fk_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes (quiz_id) ON DELETE CASCADE,
                              CONSTRAINT fk_question FOREIGN KEY (question_id) REFERENCES questions (question_id) ON DELETE CASCADE
);
