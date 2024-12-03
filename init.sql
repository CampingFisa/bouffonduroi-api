CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    address  VARCHAR(255) NOT NULL,
    is_admin BOOLEAN      NOT NULL
);

CREATE TABLE friend_requests (
                                 id SERIAL PRIMARY KEY,
                                 sender_id INT NOT NULL,
                                 receiver_id INT NOT NULL,
                                 status VARCHAR(20) NOT NULL,
                                 CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users (id) ON DELETE CASCADE,
                                 CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE friendships (
                             id SERIAL PRIMARY KEY,
                             user1_id INT NOT NULL,
                             user2_id INT NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             CONSTRAINT fk_user1 FOREIGN KEY (user1_id) REFERENCES users (id) ON DELETE CASCADE,
                             CONSTRAINT fk_user2 FOREIGN KEY (user2_id) REFERENCES users (id) ON DELETE CASCADE,
                             CONSTRAINT valid_friendship CHECK (user1_id <> user2_id)
);

