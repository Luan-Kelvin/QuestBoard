CREATE TABLE users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    creation_date DATE DEFAULT(CURRENT_DATE) NOT NULL
);

CREATE TABLE challenge(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    difficulty VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    creation_date DATE DEFAULT (CURRENT_DATE),
    id_creator_user BIGINT NOT NULL,

    CONSTRAINT fk_id_creator_user
    FOREIGN KEY (id_creator_user)
    REFERENCES users(id)
);

CREATE TABLE challenge_participants(
    id_challenge BIGINT NOT NULL,
    id_user BIGINT NOT NULL,

    CONSTRAINT pk_challenge_participants
            PRIMARY KEY (id_challenge, id_user),

    CONSTRAINT fk_id_challenge
    FOREIGN KEY (id_challenge)
    REFERENCES challenge(id),

    CONSTRAINT fk_id_creator_user2
    FOREIGN KEY (id_user)
    REFERENCES users(id)
);