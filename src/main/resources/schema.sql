CREATE TABLE IF NOT EXISTS mpa
(
    mpa_id INT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR UNIQUE
);

CREATE TABLE IF NOT EXISTS films
(
    film_id      INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR NOT NULL,
    description  VARCHAR(200),
    release_date DATE,
    duration     INT,
    mpa_id       INT,
    FOREIGN KEY (mpa_id) REFERENCES mpa (mpa_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id  INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR        NOT NULL,
    login    VARCHAR UNIQUE NOT NULL,
    birthday DATE,
    email    VARCHAR        NOT NULL
);

CREATE TABLE IF NOT EXISTS likes
(
    like_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    film_id INT,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (film_id) REFERENCES films (film_id)
);

CREATE TABLE IF NOT EXISTS genres
(
    genre_id INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(50) UNIQUE
);

CREATE TABLE IF NOT EXISTS film_genres
(
    film_id  INT,
    genre_id INT,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES films (film_id),
    FOREIGN KEY (genre_id) REFERENCES genres (genre_id)
);

CREATE TABLE IF NOT EXISTS friendship_statuses
(
    status_id INT AUTO_INCREMENT PRIMARY KEY,
    status    VARCHAR UNIQUE
);

CREATE TABLE IF NOT EXISTS friendships
(
    user1_id  INT,
    user2_id  INT,
    status_id INT,
    FOREIGN KEY (user1_id) REFERENCES users (user_id),
    FOREIGN KEY (user2_id) REFERENCES users (user_id),
    FOREIGN KEY (status_id) REFERENCES friendship_statuses (status_id)
);