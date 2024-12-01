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

CREATE TABLE friends (
                         id SERIAL PRIMARY KEY,
                         user_id INT NOT NULL,
                         friend_id INT NOT NULL,
                         CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                         CONSTRAINT fk_friend FOREIGN KEY (friend_id) REFERENCES users (id) ON DELETE CASCADE
);

